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
    private OrderMainMapper orderMainMapper;
    @Autowired
    private HandleAccountChangeService handleAccountChangeService;
    @Autowired
    private OrderMainService orderMainService;
    @Autowired
    private BBusinessMapper businessMapper;

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
     * 支付购买时长
     *
     * @param cid     用户di
     * @param giftId  礼包id
     * @param payType 支付类型1余额  2泰国本地银行卡
     * @param payWord 支付密码
     * @return
     */
    @Override
    public RestResult payTimeGift(Integer cid, Integer giftId, Integer payType, String payWord) {
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
        log.setPayChannel(LoginChannelEnums.APP.getChannel());
        log.setPayMoney(giftHour.getMoney());
        log.setHandleType(HandleTypeEnums.BUY_TIEM_GIFT.getId());
        log.setServiceType(ServiceEnums.BUY_TIEM_GIFT.getId());
        log.setPayCode(payCode);
        log.setCreateTime(DateUtil.currentDate());
        boolean flag = cPayLogService.save(log);
        if (!flag) {
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
        }
        return payService.handleSign(log);
    }

    /**
     * 购买充电宝
     *
     * @param cid
     * @param payType
     * @param payWord
     * @return
     */
    @Override
    public RestResult payBuyPowerBank(Integer cid, Integer payType, String payWord) {
        BigDecimal money = new BigDecimal(Integer.parseInt(infoParamService.getValueByCode("buyPowerBankPrice")));
        OrderMain orderMain = orderMainMapper.selectOne(new QueryWrapper<OrderMain>().eq("cid", cid).eq("status", 1));
        BBusiness business = businessMapper.selectOne(new QueryWrapper<BBusiness>().eq("cid", orderMain.getCid()));
        // 计算一共使用了多少个小时
        orderMain.setReturnTime(DateUtil.currentDate());
        // 操作时长-余额
        int hour = DateUtil.dateDiffHour(orderMain.getRentTime(), orderMain.getReturnTime());
        // 查询是否有时长
        CAccount account = cAccountService.getByCid(orderMain.getCid());
        if (account == null) {
            return RestResult.fail("fail", "00");
        }
        String flag = null;
        String msg = "";
        Integer orderStatus = OrderStatusEnums.CONFIRMED.getId();
        String valueStr = infoParamService.getValueByCode(ParamCodeEnums.USE_HOUR_MONEY.getCode());
        // 一小时多少钱
        BigDecimal value = FunctionUtils.getValueByClass(BigDecimal.class, valueStr);
        // 租金总额
        BigDecimal rentMoney = FunctionUtils.mul(value, new BigDecimal(hour), 2);
        // 实际扣除时长
        Integer realHour = hour;
        // 实际扣除余额
        BigDecimal payMoney = BigDecimal.ZERO;
        // 有时长，先扣时长
        // 时长足够
        if (account.getHour() > 0 && account.getHour() >= hour) {
            // 时长足够，不管是否开启免密支付，订单都会使用时长支付-变成已完成
            orderMain.setPayType(PayTypeEnums.MONEY.getId());
            orderMain.setOverTime(DateUtil.currentDate());
            orderStatus = OrderStatusEnums.OVER.getId();
        } else {
            // 时长不足——先有多少扣多少
            realHour = account.getHour();
            // 扣除时长后计算应该扣多少余额
            payMoney = FunctionUtils.mul(value, new BigDecimal(hour - realHour), 2);
            // 判断是否免密支付-免密支付余额充足的情况下，订单状态变成已完成
            if (FunctionUtils.isEquals(StaticUtils.STATUS_YES, account.getFreeSecret())) {
                // 开启了免密支付-扣除余额
                msg = handleAccountChangeService.insertCMoneyDetail(new CMoneyDetail(orderMain.getCid(), HandleTypeEnums.ORDER.getId(),
                        StaticUtils.PAY_OUT, orderMain.getPayCode(), payMoney, HandleTypeEnums.getName(HandleTypeEnums.ORDER.getId())));
                // 余额充足
                if (StringUtils.isEmpty(msg)) {
                    orderMain.setPayType(PayTypeEnums.MONEY.getId());
                    orderMain.setOverTime(DateUtil.currentDate());
                    orderStatus = OrderStatusEnums.OVER.getId();
                } else {
                    // 余额不足或其它情况,flag标识余额不足
                    flag = StaticUtils.RETURN_MONEY_NOT_ENOUGH;
                }
            } else {
                // 未开启免密支付-余额不用扣除
                flag = StaticUtils.RETURN_NOT_FREE_SECRET;
            }
        }
        // 统一扣除时长
        if (realHour > 0) {
            msg = handleAccountChangeService.insertCHourDetail(new CHourDetail(orderMain.getCid(), HandleTypeEnums.ORDER.getId(),
                    StaticUtils.PAY_OUT, orderMain.getPayCode(), realHour, HandleTypeEnums.getName(HandleTypeEnums.ORDER.getId())));
            if (StringUtils.isNotEmpty(msg)) {
                return RestResult.fail("fail", "00");
            }
        }
        // 操作赋值
        BigDecimal realMoney = payMoney;
        orderMain.setRentMoney(rentMoney);
        orderMain.setRealMoney(realMoney);
        orderMain.setPayMoney(payMoney);
        orderMain.setHour(realHour);
        orderMain.setOrderStatus(orderStatus);
        orderMain.setReturnAddr(business.getAddressDetail());
        boolean update = orderMainService.updateById(orderMain);
        if (!update) {
            // 数据操作失败
            return RestResult.fail("fail", "00");
        }

        //扣除押金
        account.setDeposit(FunctionUtils.sub(account.getDeposit(), money, 2));
        account.setRechargeDeposit(0);
        CMarginDetail marginDetail = new CMarginDetail();
        marginDetail.setCid(orderMain.getCid());
        marginDetail.setIsout(1);
        marginDetail.setCost(money);
        marginDetail.setCreateTime(new Date());
        //marginDetail.setPayCode(orderMain.getPayCode());
        marginDetail.setRemark("购买充电宝");
        int insert = marginDetailMapper.insert(marginDetail);
        if (insert == 0) {
            return RestResult.success("fail");
        }
        return RestResult.success("success");

        /*if (money == null) {
            return RestResult.fail("param_abnormal");
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
                //余额是否大于系统设置金额
                if (account.getMoney().compareTo(money) != 1) {
                return RestResult.fail("pay_balance_error");
            }
        }
        String payCode = FunctionUtils.getOrderCode("T");
        CPayLog log = new CPayLog();
        log.setCid(cid);
        log.setPayType(payType);
        log.setPayChannel(LoginChannelEnums.APP.getChannel());
        log.setPayMoney(money);
        log.setHandleType(HandleTypeEnums.BUY_TIEM_GIFT.getId());
        log.setServiceType(ServiceEnums.BUY_TIEM_GIFT.getId());
        log.setPayCode(payCode);
        log.setCreateTime(DateUtil.currentDate());
        boolean flag = cPayLogService.save(log);
        if (!flag) {
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
        }
        return payService.handleSign(log);*/
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
    public Map<String, Object> getRechargeMsg() {
        String value = infoParamService.getValueByCode(ParamCodeEnums.RECHARGE_DEPOSIT_MONEY.getCode());
        BigDecimal depositMoney = FunctionUtils.getValueByClass(BigDecimal.class, value);
        Map<String, Object> map = new HashMap<>();
        map.put("depositMoney", depositMoney);
        RuleTextVo ruleMoney = sysRuleTextService.getByType(1);
        RuleTextVo ruleDeposit = sysRuleTextService.getByType(2);
        map.put("ruleMoney", ruleMoney);
        map.put("ruleDeposit", ruleDeposit);
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
