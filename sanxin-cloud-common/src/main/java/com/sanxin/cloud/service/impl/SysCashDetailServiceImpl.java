package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.pwd.Encode;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.enums.CashTypeEnums;
import com.sanxin.cloud.enums.ServiceEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CAccountMapper;
import com.sanxin.cloud.mapper.CMarginDetailMapper;
import com.sanxin.cloud.mapper.SysCashDetailMapper;
import com.sanxin.cloud.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 余额提现记录表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
@Service
public class SysCashDetailServiceImpl extends ServiceImpl<SysCashDetailMapper, SysCashDetail> implements SysCashDetailService {
    @Autowired
    private BankDetailService bankDetailService;
    @Autowired
    private SysCashRuleService sysCashRuleService;
    @Autowired
    private SysCashDetailService sysCashDetailService;
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private BBusinessService businessService;
    @Autowired
    private CMarginDetailMapper marginDetailMapper;
    @Autowired
    private CAccountMapper accountMapper;

    @Override
    public RestResult handleCashStatus(Integer id, Integer status) {
        SysCashDetail cash = super.getById(id);
        if (status != null && FunctionUtils.isEquals(cash.getStatus(), StaticUtils.STATUS_APPLY)) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_REPEAT_SUBMIT));
        }
        cash.setStatus(status);
        cash.setEndTime(new Date());
        boolean save = super.updateById(cash);
        if (!save) {
            throw new ThrowJsonException(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        // 如果是驳回,将金额退还
        if (FunctionUtils.isEquals(cash.getStatus(), StaticUtils.STATUS_FAIL)) {

        } else if (FunctionUtils.isEquals(cash.getStatus(), StaticUtils.STATUS_SUCCESS)) {

        }
        return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
    }

    @Override
    public RestResult handleCashApply(SysCashDetail cashDetail, String payWord, String validCode) {
        // 校验密码
        if (StringUtils.isBlank(payWord)) {
            return RestResult.fail("pay_word_empty");
        }
        // 校验提现人信息
        RestResult valid = validCash(cashDetail, payWord);
        if (!valid.status) {
            return valid;
        }
        BankDetail bankDetail = bankDetailService.getById(cashDetail.getBankId());
        if (bankDetail == null) {
            return RestResult.fail("cash_bank_empty");
        }

        // 获取提现规则
        SysCashRule cashRule = sysCashRuleService.getRuleByType(cashDetail.getType());
        if (!FunctionUtils.isEquals(StaticUtils.STATUS_YES, cashRule.getIsOpen())) {
            return RestResult.fail("cash_close");
        }
        BigDecimal cashMoney = cashDetail.getCashMoney();
        if (cashMoney == null || cashMoney.compareTo(cashRule.getMinVal()) < 0
                || cashMoney.compareTo(cashRule.getMaxVal()) > 0) {
            String[] val = {cashRule.getMinVal().toString(), cashRule.getMaxVal().toString()};
            return RestResult.fail(LanguageUtils.getMessage("cash_money_scope", val));
        }
        if (cashMoney.doubleValue() % cashRule.getMultiple() != 0) {
            String[] val = {cashRule.getMultiple().toString()};
            return RestResult.fail(LanguageUtils.getMessage("cash_multiple", val));
        }
        // 限制每日提现次数
        if (!FunctionUtils.isEquals(StaticUtils.CASH_NO_TYPE, cashRule.getType())) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("targetId", cashDetail.getTargetId());
            map.put("type", cashDetail.getType());
            map.put("cashType", cashRule.getType());
            Integer num = baseMapper.getRuleNum(map);
            if (num > cashRule.getNum()) {
                return RestResult.fail("cash_num_long");
            }
        }

        BigDecimal tax = BigDecimal.ZERO;
        if (FunctionUtils.isEquals(cashDetail.getType(), CashTypeEnums.BUSINESS.getId())) {
            if (cashDetail.getTax() == null) {
                return RestResult.fail("cash_tax_empty");
            }
            tax = cashDetail.getTax();
        }

        String payCode = FunctionUtils.getOrderCode(ServiceEnums.CASH.name());
        // 操作——扣除余额

        // 提现手续费比例
        BigDecimal scale = cashRule.getScale();
        scale = FunctionUtils.add(scale, tax, 4);
        BigDecimal scaleMoney = FunctionUtils.mul(cashMoney, scale, 2);

        // 实际应该到账金额
        BigDecimal realMoney = FunctionUtils.sub(cashMoney, scaleMoney, 2);
        SysCashDetail detail = new SysCashDetail();
        detail.setPayCode(payCode);
        detail.setTargetId(cashDetail.getTargetId());
        detail.setRealName(bankDetail.getRealName());
        detail.setIdCard(bankDetail.getCardNo());
        detail.setBankName(bankDetail.getBankName());
        detail.setBankCode(bankDetail.getBankCard());
        detail.setPhone(bankDetail.getPhone());
        detail.setBankAddr(bankDetail.getBankAddr());
        detail.setCashMoney(cashMoney);
        detail.setRealMoney(realMoney);
        detail.setType(cashDetail.getType());
        detail.setBankId(cashDetail.getBankId());
        detail.setTax(tax);
        boolean result = sysCashDetailService.save(detail);
        if (!result) {
            throw new ThrowJsonException(LanguageUtils.getMessage("fail"));
        }
        return RestResult.success("success");
    }

    /**
     * 用户点击提现申请返回判断支付方式(押金)查询最后一条充值记录
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult selectLimt(Integer cid) {
        CMarginDetail detail = marginDetailMapper.selectLimt(cid);
        if (detail != null && detail.getIsout() == 0) {
            return RestResult.success(detail.getType());
        }
        return RestResult.fail("withdraw_judge");
    }

    /**
     * 确认申请提现
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult marginWithdraw(Integer cid) {
        CMarginDetail detail = marginDetailMapper.selectLimt(cid);
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        if (account.getDeposit().compareTo(BigDecimal.ZERO) == 0) {
            return RestResult.fail("withdraw_apply");
        }
        CMarginDetail marginDetail = new CMarginDetail();
        //如果是余额支付 提现流程(直接提现到余额)
        if (detail.getType() == 1) {
            BigDecimal deposit = account.getDeposit();
            account.setMoney(FunctionUtils.add(account.getMoney(), account.getDeposit(), 2));
            account.setDeposit(BigDecimal.ZERO);

            marginDetail.setCid(cid);
            marginDetail.setCost(deposit);
            marginDetail.setCreateTime(new Date());
            marginDetail.setIsout(0);

        }
        //如果是其他方式提现流程(未写支付所以空着)
        //TODO 未写支付所以空着
        if (detail.getType() == 0) {

        }

        int m = marginDetailMapper.insert(marginDetail);
        int a = accountMapper.updateById(account);
        if (a == 0 || m == 0) {
            new ThrowJsonException("插入或者更新失败");
        }
        return RestResult.success("success");
    }

    @Override
    public BigDecimal sumCashMoney() {
        return baseMapper.sumCashMoney();
    }

    /**
     * 校验用户、商家的信息
     *
     * @param cashDetail
     * @param payWord
     * @return
     */
    public RestResult validCash(SysCashDetail cashDetail, String payWord) {
        if (FunctionUtils.isEquals(cashDetail.getType(), CashTypeEnums.CUSTOMER.getId())) {
            CCustomer customer = customerService.getById(cashDetail.getTargetId());
            if (customer == null) {
                return RestResult.fail("data_exception");
            }
            // 校验支付密码
            String encPayWord = PwdEncode.encodePwd(payWord);
            if (!encPayWord.equals(customer.getPayWord())) {
                if (StringUtils.isBlank(customer.getPayWord())) {
                    return RestResult.fail("您还未设置支付密码", null, "1");
                } else {
                    return RestResult.fail("支付密码不正确");
                }
            }
        } else if (FunctionUtils.isEquals(cashDetail.getType(), CashTypeEnums.BUSINESS.getId())) {
            BBusiness business = businessService.getById(cashDetail.getTargetId());
            if (business == null) {
                return RestResult.fail("data_exception");
            }
            // 校验支付密码
            String encPayWord = PwdEncode.encodePwd(payWord);
            if (!encPayWord.equals(business.getPayWord())) {
                if (StringUtils.isBlank(business.getPayWord())) {
                    return RestResult.fail("您还未设置支付密码", null, "1");
                } else {
                    return RestResult.fail("支付密码不正确");
                }
            }
        } else {
            return RestResult.fail("data_exception");
        }
        return RestResult.success("");
    }
}
