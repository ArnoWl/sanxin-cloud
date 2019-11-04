package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.app.api.service.AccountService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.*;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.enums.*;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.*;
import com.sanxin.cloud.service.*;
import com.sanxin.cloud.service.system.pay.HandleAccountChangeService;
import com.sanxin.cloud.service.system.pay.PayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * @author xiaoky
 * @date 2019-10-09
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CMarginDetailMapper marginDetailMapper;
    @Autowired
    private BankDetailMapper bankDetailMapper;
    @Autowired
    private CMoneyDetailMapper moneyDetailMapper;
    @Autowired
    private CTimeDetailMapper timeDetailMapper;
    @Autowired
    private CAccountService cAccountService;
    @Autowired
    private InfoParamService infoParamService;
    @Autowired
    private PayService payService;
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private CPayLogService cPayLogService;
    @Autowired
    private GiftHourMapper giftHourMapper;
    @Autowired
    private SysRuleTextService sysRuleTextService;
    @Autowired
    private CAccountMapper accountMapper;

    /**
     * 我的押金
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult queryMyDeposit(SPage<CMarginDetail> page, Integer cid) {
        CMarginVO marginVO = new CMarginVO();
        Page<CMarginDetail> list = marginDetailMapper.queryMyDepositList(page, cid);
        CAccount account = getAccount(cid);
        marginVO.setList(list);
        marginVO.setMargin(account.getDeposit());
        return RestResult.success(marginVO);
    }

    /**
     * 我的钱包
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult queryMyPurse(Integer cid) {
        CAccount account = getAccount(cid);
        Integer count = bankDetailMapper.selectCount(new QueryWrapper<BankDetail>().eq("target_id", cid));
        account.setCard(count);
        return RestResult.success(account);
    }

    /**
     * 余额明细
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult queryBalanceDetail(SPage<CMoneyDetail> page, Integer cid) {
        MoneyDetailVO moneyDetailVO = new MoneyDetailVO();
        Page<CMoneyDetail> list = moneyDetailMapper.queryBalanceDetail(page, cid);
        CAccount account = getAccount(cid);
        moneyDetailVO.setList(list);
        moneyDetailVO.setBalance(account.getMoney());
        return RestResult.success(moneyDetailVO);
    }

    /**
     * 我要充值显示余额
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult getBalance(Integer cid) {
        BalanceVO vo = new BalanceVO();
        CAccount account = getAccount(cid);
        if (account != null) {
            vo.setBalance(account.getMoney());
        }
        List<Integer> list = new ArrayList<>();
        list.add(StaticUtils.TWENTY);
        list.add(StaticUtils.THIRTY);
        list.add(StaticUtils.FIFTY);
        list.add(StaticUtils.SIXTY);
        list.add(StaticUtils.SEVENTY);
        list.add(StaticUtils.EIGHTY);
        vo.setList(list);
        return RestResult.success(vo);
    }

    /**
     * 用户时长明细
     *
     * @param page
     * @param cid
     * @return
     */
    @Override
    public RestResult queryTimeDetail(SPage<CTimeDetail> page, Integer cid) {
        UserTimeVO userTimeVO = new UserTimeVO();
        SPage<CTimeDetail> list = timeDetailMapper.queryTimeDetail(page, cid);
        for (CTimeDetail record : list.getRecords()) {
            record.setTypeName(HandleTypeEnums.getName(record.getType()));
        }
        CAccount account = getAccount(cid);
        userTimeVO.setList(list);
        userTimeVO.setTime(account.getHour());
        return RestResult.success(userTimeVO);
    }

    /**
     * 剩余时长
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult getBuyGift(Integer cid) {
        BuyGiftVO giftVO = new BuyGiftVO();
        CAccount account = getAccount(cid);
        if (account != null) {
            giftVO.setHour(account.getHour());
        }
        List<GiftHour> giftHours = giftHourMapper.selectList(null);
        giftVO.setList(giftHours);
        RuleTextVo byType = sysRuleTextService.getByType(3);
        if (byType != null) {
            giftVO.setRuleTextVo(byType);
        }
        return RestResult.success(giftVO);
    }

    /**
     * 领取赠送时长
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult payReceiveTimeGift(Integer cid) {
        CTimeDetail type = timeDetailMapper.selectOne(new QueryWrapper<CTimeDetail>().eq("type", TimeGiftEnums.BUY.getId()).eq("cid", cid));
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        if (type != null) {
            return RestResult.fail("您已领取过");
        }
        CTimeDetail timeDetail=new CTimeDetail();
        timeDetail.setCid(cid);
        timeDetail.setType(TimeGiftEnums.BUY.getId());
        timeDetail.setIsout(1);
        timeDetail.setOriginal(new BigDecimal(account.getHour()));
        timeDetail.setLast(FunctionUtils.add(new BigDecimal(account.getHour()),new BigDecimal(1),2));
        timeDetail.setCreateTime(new Date());
        timeDetail.setRemark(TimeGiftEnums.BUY.getName());
        return null;
    }

    /**
     * 支付购买时长
     *
     * @param cid     用户di
     * @param giftId  礼包id
     * @param payType 支付类型1余额  2泰国本地银行卡
     * @param payWord 支付密码
     * @return
     */
    @Override
    public RestResult payTimeGift(Integer cid, Integer giftId, Integer payType, String payWord, Integer payChannel) {
        GiftHour giftHour = giftHourMapper.selectById(giftId);
        if (giftHour == null) {
            return RestResult.fail("gift_abnormal");
        }
        CCustomer customer = customerService.getById(cid);
        if (customer == null) {
            return RestResult.fail("register_user_empty");
        }
        CAccount account = cAccountService.getByCid(cid);
        switch (payType) {
            //余额支付
            case 1:
                //密码加密
                String pass = PwdEncode.encodePwd(payWord);
                //交易密码是否为空
                if (StringUtils.isBlank(customer.getPayWord())) {
                    return RestResult.fail("not_set_pay_word", null, "1");
                }
                //交易密码是否匹配
                if (!pass.equals(customer.getPayWord())) {
                    return RestResult.fail("pay_word_error");
                }
                //余额是否大于礼包金额
                if (account.getMoney().compareTo(giftHour.getMoney()) != 1) {
                    return RestResult.fail("pay_balance_error");
                }

        }
        String payCode = FunctionUtils.getOrderCode("T");
        CPayLog log = new CPayLog();
        log.setCid(cid);
        log.setPayType(payType);
        log.setPayMoney(giftHour.getMoney());
        log.setHandleType(HandleTypeEnums.BUY_TIEM_GIFT.getId());
        log.setServiceType(ServiceEnums.BUY_TIEM_GIFT.getId());
        log.setPayCode(payCode);
        log.setCreateTime(DateUtil.currentDate());
        log.setPayChannel(payChannel);
        boolean flag = cPayLogService.save(log);
        if (!flag) {
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
        }
        return payService.handleSign(log);
    }

    /**
     * 余额充值
     * @param cid
     * @param payType
     * @param payChannel
     * @param payMoney
     * @return
     */
    @Override
    public RestResult payBalanceRecharge(Integer cid, Integer payType, Integer payChannel, BigDecimal payMoney) {
        CCustomer customer = customerService.getById(cid);
        if (customer == null) {
            return RestResult.fail("register_user_empty");
        }
        // 判断参数值
        if (payType == null) {
            return RestResult.fail("pay_type_empty");
        }
        if (payChannel == null) {
            return RestResult.fail("pay_channel_empty");
        }
        // 数据赋值-签名
        String payCode = FunctionUtils.getOrderCode("B");
        CPayLog log = new CPayLog();
        log.setCid(cid);
        log.setPayType(payType);
        log.setPayChannel(payChannel);
        log.setPayMoney(payMoney);
        log.setHandleType(HandleTypeEnums.BALANCE_RECHARGE.getId());
        log.setServiceType(ServiceEnums.BALANCE_RECHARGE.getId());
        log.setPayCode(payCode);
        boolean flag = cPayLogService.save(log);
        if (!flag) {
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
        }
        return payService.handleSign(log);
    }

    /**
     * 获取用户账户
     *
     * @param cid
     * @return
     */
    private CAccount getAccount(Integer cid) {
        CAccount account = cAccountService.getByCid(cid);
        if (account == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        return account;
    }

    /**
     * 充值押金处理
     *
     * @param cid
     * @param payWord    支付密码-选择余额支付需要
     * @param payType    支付方式见PayTypeEnums
     * @param payChannel 支付渠道见LoginChannelEnums
     * @param freeSecret 是否免密支付
     * @return
     */
    @Override
    public RestResult handleRechargeDeposit(Integer cid, String payWord, Integer payType, Integer payChannel, Integer freeSecret) {
        CCustomer customer = customerService.getById(cid);
        if (customer == null) {
            return RestResult.fail("register_user_empty");
        }
        // 判断参数值
        if (payType == null) {
            return RestResult.fail("pay_type_empty");
        }
        if (payChannel == null) {
            return RestResult.fail("pay_channel_empty");
        }
        if (freeSecret == null) {
            freeSecret = StaticUtils.STATUS_NO;
        }
        // 判断是否交过押金
        CAccount account = cAccountService.getByCid(cid);
        if (FunctionUtils.isEquals(account.getRechargeDeposit(), StaticUtils.STATUS_YES)) {
            return RestResult.fail("deposit_is_have");
        }
        // 余额支付判断支付密码
        if (FunctionUtils.isEquals(payType, PayTypeEnums.MONEY.getId())) {
            // 判断是否余额支付——余额支付需要校验密码
            if (StringUtils.isBlank(payWord)) {
                return RestResult.fail("pay_word_empty");
            }
            String encryPayword = PwdEncode.encodePwd(payWord);
            if (!encryPayword.equals(customer.getPayWord())) {
                if (StringUtils.isBlank(customer.getPayWord())) {
                    return RestResult.fail("not_set_pay_word", null, "1");
                } else {
                    return RestResult.fail("pay_word_error");
                }
            }
        }
        // 数据赋值-签名
        BigDecimal payMoney = FunctionUtils.getValueByClass(BigDecimal.class, infoParamService.getValueByCode(ParamCodeEnums.RECHARGE_DEPOSIT_MONEY.getCode()));
        String payCode = FunctionUtils.getOrderCode("P");
        CPayLog log = new CPayLog();
        log.setCid(cid);
        log.setPayType(payType);
        log.setPayChannel(payChannel);
        log.setPayMoney(payMoney);
        log.setHandleType(HandleTypeEnums.RECHARGE_DEPOSIT_MONEY.getId());
        log.setServiceType(ServiceEnums.RECHARGE_DEPOSIT_MONEY.getId());
        log.setPayCode(payCode);
        log.setParams(freeSecret.toString());
        boolean flag = cPayLogService.save(log);
        if (!flag) {
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
        }
        return payService.handleSign(log);
    }

    /**
     * 支付方式列表
     *
     * @param type 支付渠道
     * @param cid  用户id
     * @return
     */
    @Override
    public Map<String, Object> queryPayTypeList(Integer type, Integer cid) {
        CAccount cAccount = cAccountService.getByCid(cid);
        if (cAccount == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        if (!PayPageEnums.isPayPage(type)) {
            throw new ThrowJsonException("request_error");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", PayTypeEnums.queryListByPayPage(type));
        map.put("money", cAccount.getMoney());
        return map;
    }

    /**
     * 支付方式列表——小程序
     *
     * @param cid 用户id
     * @return
     */
    @Override
    public Map<String, Object> queryPayTypeListForProgram(Integer cid) {
        CAccount cAccount = cAccountService.getByCid(cid);
        if (cAccount == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", PayTypeEnums.queryListForProgramByPayPage());
        map.put("money", cAccount.getMoney());
        return map;
    }

    @Override
    public Map<String, Object> getRechargeMsg(Integer id) {
        String value = infoParamService.getValueByCode(ParamCodeEnums.RECHARGE_DEPOSIT_MONEY.getCode());
        CAccount account = accountMapper.selectById(id);
        BigDecimal depositMoney = FunctionUtils.getValueByClass(BigDecimal.class, value);
        Map<String, Object> map = new HashMap<>();
        map.put("depositMoney", depositMoney);
        RuleTextVo ruleMoney = sysRuleTextService.getByType(1);
        RuleTextVo ruleDeposit = sysRuleTextService.getByType(2);
        map.put("ruleMoney", ruleMoney);
        map.put("ruleDeposit", ruleDeposit);
        map.put("freeSecret", account.getFreeSecret());
        return map;
    }

    /**
     * 开启关闭免密支付
     *
     * @param cid 用户cid
     * @return
     */
    @Override
    public RestResult freeSecret(Integer cid, String payWord) {
        CAccount account = cAccountService.getByCid(cid);
        if (account.getFreeSecret() == 0) {
            if (payWord != null) {
                CCustomer customer = customerService.getById(cid);
                //加密密码
                String pass = PwdEncode.encodePwd(payWord);
                if (!pass.equals(customer.getPayWord())) {
                    return RestResult.fail("pay_word_error");
                }
            } else {
                return RestResult.fail("pay_word_empty");
            }
            account.setFreeSecret(1);
        } else {
            account.setFreeSecret(0);
        }
        boolean update = cAccountService.updateById(account);
        if (!update) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }
}
