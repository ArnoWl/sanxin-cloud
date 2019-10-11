package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.app.api.service.AccountService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.*;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.enums.HandleTypeEnums;
import com.sanxin.cloud.enums.ParamCodeEnums;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.enums.ServiceEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.*;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.CCustomerService;
import com.sanxin.cloud.service.CPayLogService;
import com.sanxin.cloud.service.InfoParamService;
import com.sanxin.cloud.service.system.pay.PayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
     * @param cid
     * @return
     */
    @Override
    public RestResult getBalance(Integer cid) {
        BalanceVO vo=new BalanceVO();
        CAccount account = getAccount(cid);
        if (account != null) {
            vo.setBalance(account.getMoney());
        }
        List<Integer> list=new ArrayList<>();
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
     * @param page
     * @param cid
     * @return
     */
    @Override
    public RestResult queryTimeDetail(SPage<CTimeDetail> page, Integer cid) {
        UserTimeVO userTimeVO = new UserTimeVO();
        SPage<CTimeDetail> list = timeDetailMapper.queryTimeDetail(page, cid);
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
        BuyGiftVO giftVO=new BuyGiftVO();
        CAccount account = getAccount(cid);
        if (account != null) {
            giftVO.setHour(account.getHour());
        }
        List<GiftHour> giftHours = giftHourMapper.selectList(null);
        giftVO.setList(giftHours);
        return RestResult.success(giftVO);
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

    @Override
    public RestResult rechargeDeposit(Integer cid, String payWord, Integer payType, Integer payChannel, Integer freeSecret) {
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
}
