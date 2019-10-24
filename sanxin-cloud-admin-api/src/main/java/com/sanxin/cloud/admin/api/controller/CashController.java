package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BankType;
import com.sanxin.cloud.entity.SysCashDetail;
import com.sanxin.cloud.entity.SysCashRule;
import com.sanxin.cloud.enums.CashTypeEnums;
import com.sanxin.cloud.service.BankTypeService;
import com.sanxin.cloud.service.SysCashDetailService;
import com.sanxin.cloud.service.SysCashRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 提现相关Controller
 * @author xiaoky
 * @date 2019-09-03
 */
@RestController
@RequestMapping(value = "/cash")
public class CashController {
    @Autowired
    private SysCashRuleService sysCashRuleService;
    @Autowired
    private SysCashDetailService sysCashDetailService;
    @Autowired
    private BankTypeService bankTypeService;

    /**
     * 获取用户提现规则
     * @return
     */
    @GetMapping("/getCustomerCashRule")
    public RestResult getCustomerCashRule() {
        SysCashRule cashRule = sysCashRuleService.getRuleByType(CashTypeEnums.CUSTOMER.getId());
        return RestResult.success("", cashRule);
    }

    /**
     * 获取用户提现规则
     * @return
     */
    @GetMapping("/getBusinessCashRule")
    public RestResult getBusinessCashRule() {
        SysCashRule cashRule = sysCashRuleService.getRuleByType(CashTypeEnums.BUSINESS.getId());
        return RestResult.success("", cashRule);
    }

    /**
     * 修改提现规则
     * @param sysCashRule 提现规则数据
     * @return
     */
    @PostMapping("/updateCashRule")
    public RestResult updateCashRule(SysCashRule sysCashRule) {
        if (sysCashRule == null || sysCashRule.getType() == null || sysCashRule.getIsOpen() == null) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        if (sysCashRule.getNum() == null) {
            return RestResult.fail(AdminLanguageStatic.CASH_NUM);
        }
        if (sysCashRule.getScale() == null) {
            return RestResult.fail(AdminLanguageStatic.CASH_SCALE);
        }
        if (sysCashRule.getMinVal() == null) {
            return RestResult.fail(AdminLanguageStatic.CASH_MINVAL);
        }
        if (sysCashRule.getMaxVal() == null) {
            return RestResult.fail(AdminLanguageStatic.CASH_MAXVAL);
        }
        if (sysCashRule.getMultiple() == null) {
            return RestResult.fail(AdminLanguageStatic.CASH_MULTIPLE);
        }
        if (!FunctionUtils.isEquals(sysCashRule.getType(), CashTypeEnums.BUSINESS.getId())) {
            sysCashRule.setTaxOne(BigDecimal.ZERO);
            sysCashRule.setTaxTwo(BigDecimal.ZERO);
        }
        boolean result = sysCashRuleService.updateById(sysCashRule);
        if (result) {
            return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
        }
        return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
    }

    /**
     * 查询余额提现列表
     * @param page 分页
     * @param cashDetail 查询条件
     * @return
     */
    @GetMapping(value = "/queryCashList")
    public RestResult queryCashList(SPage<SysCashDetail> page, SysCashDetail cashDetail) {
        QueryWrapper<SysCashDetail> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(cashDetail.getPayCode())) {
            wrapper.eq("pay_code", cashDetail.getPayCode());
        }
        if (StringUtils.isNotBlank(cashDetail.getPhone())) {
            wrapper.eq("phone", cashDetail.getPhone());
        }
        if (cashDetail.getStatus() != null) {
            wrapper.eq("status", cashDetail.getStatus());
        }
        sysCashDetailService.page(page, wrapper);
        return RestResult.success("", page);
    }

    /**
     * 通过或驳回提现
     * @param id
     * @param status
     * @return
     */
    @PostMapping(value = "/handleStatus")
    public RestResult handleStatus(Integer id, Integer status) {
        RestResult result = sysCashDetailService.handleCashStatus(id, status);
        return result;
    }

    /**
     * 查询银行卡类型
     * @param page 分页
     * @param bankType 银行卡类型
     * @return
     */
    @GetMapping(value = "/queryBankTypeList")
    public RestResult queryBankTypeList(SPage<BankType> page, BankType bankType) {
        bankTypeService.page(page);
        return RestResult.success("", page);
    }

    /**
     * 修改银行卡类型状态
     * @param id
     * @param status 状态
     * @return
     */
    @PostMapping(value = "/handleBankTypeStatus")
    public RestResult handleBankTypeStatus(Integer id, Integer status) {
        BankType bankType = bankTypeService.getById(id);
        if (status != null && FunctionUtils.isEquals(bankType.getStatus(), status)) {
            return RestResult.fail(AdminLanguageStatic.BASE_REPEAT_SUBMIT);
        }
        bankType.setStatus(status);
        boolean result = bankTypeService.updateById(bankType);
        if (!result) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
    }

    /**
     * 添加或编辑银行卡类型
     * @param bankType 银行卡类型
     * @return
     */
    @PostMapping(value = "/handleBankType")
    public RestResult handleBankType(BankType bankType) {
        if (StringUtils.isBlank(bankType.getBankName())) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        if (bankType.getId() == null && bankType.getLogo() == null) {
            return RestResult.fail(AdminLanguageStatic.CASH_LOGO);
        }
        boolean result = bankTypeService.saveOrUpdate(bankType);
        if (!result) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
    }
}
