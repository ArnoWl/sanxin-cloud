package com.sanxin.cloud.app.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BankDetail;
import com.sanxin.cloud.entity.SysCashDetail;
import com.sanxin.cloud.enums.CashTypeEnums;
import com.sanxin.cloud.service.BankDetailService;
import com.sanxin.cloud.service.SysCashDetailService;
import com.sanxin.cloud.service.SysCashRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 提现相关Controller
 * @author xiaoky
 * @date 2019-09-12
 */
@RestController
@RequestMapping("/cash")
public class CashController {
    @Autowired
    private BankDetailService bankDetailService;
    @Autowired
    private SysCashRuleService sysCashRuleService;
    @Autowired
    private SysCashDetailService sysCashDetailService;
    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 查询银行卡列表
     * @return
     */
    @GetMapping(value = "/queryBankList")
    public RestResult queryBankList(SPage<BankDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer targetId = loginTokenService.validLoginTid(token);
        Integer type = loginTokenService.validLoginType(token);
        QueryWrapper<BankDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("target_id", targetId).eq("type", type);
        bankDetailService.page(page, wrapper);
        for (BankDetail b : page.getRecords()) {
            b.setTargetId(null);
            b.setBankCard(FunctionUtils.getLastValue(b.getBankCard(), 4));
        }
        return RestResult.success("", page);
    }

    /**
     * 删除银行卡
     * @param bankId 银行卡id
     * @return
     */
    @DeleteMapping("/deleteBank")
    public RestResult deleteBank(Integer bankId) {
        String token = BaseUtil.getUserToken();
        Integer targetId = loginTokenService.validLoginTid(token);
        Integer type = loginTokenService.validLoginType(token);
        QueryWrapper<BankDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("id", bankId).eq("target_id", targetId).eq("type", type);
        BankDetail detail = bankDetailService.getOne(wrapper);
        if (detail == null) {
            return RestResult.fail("bank_empty");
        }
        boolean result = bankDetailService.removeById(bankId);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 添加银行卡
     * @param bankDetail 数据
     * @return
     */
    @PostMapping(value = "/addBank")
    public RestResult addBankDetail(BankDetail bankDetail) {
        String token = BaseUtil.getUserToken();
        Integer targetId = loginTokenService.validLoginTid(token);
        Integer type = loginTokenService.validLoginType(token);
        RestResult result = bankDetailService.addBankDetail(bankDetail);
        return result;
    }

    /**
     * 获取银行卡详情-用于编辑银行卡数据回显
     * @param bankId
     * @return
     */
    @GetMapping(value = "/getBank")
    public RestResult getBankDetail(Integer bankId) {
        String token = BaseUtil.getUserToken();
        Integer targetId = loginTokenService.validLoginTid(token);
        Integer type = loginTokenService.validLoginType(token);
        BankDetail bankDetail = bankDetailService.getById(bankId);
        if (bankDetail == null) {
            return RestResult.fail("data_exception");
        }
        // 判断是否本人银行卡
        if (!FunctionUtils.isEquals(bankDetail.getType(), type)
                || !FunctionUtils.isEquals(bankDetail.getTargetId(), targetId)) {
            return RestResult.fail("data_exception");
        }

        bankDetail.setTargetId(null);
        return RestResult.success("success", bankDetail);
    }

    /**
     * 修改银行卡信息
     * @param bankDetail
     * @return
     */
    @PutMapping("/updateBank")
    public RestResult updateBankDetail(BankDetail bankDetail) {
        RestResult result = bankDetailService.updateBankDetail(bankDetail);
        return result;
    }

    /**
     * 获取提现比例等规则
     * @param type 根据类型查询提现规则
     * @return
     */
    @GetMapping(value = "/getCashScale")
    public RestResult getCashScale(Integer type) {
        Map<String, Object> map = sysCashRuleService.getCashRule(type);
        return RestResult.success("success", map);
    }

    /**
     * 处理用户提现申请
     * @param cashDetail
     * @return
     */
    @PostMapping("/handleCashApply")
    public RestResult handleCashApply(SysCashDetail cashDetail, String payWord, String validCode) {
        return sysCashDetailService.handleCashApply(cashDetail, payWord, validCode);
    }


    /**
     * 用户点击提现申请返回判断支付方式(押金)查询最后一条充值记录
     * @return
     */
    @GetMapping(MappingUtils.SELECT_LIMT)
    public RestResult selectLimt() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return sysCashDetailService.selectLimt(cid);
    }

    /**
     * 确认申请提现
     * @return
     */
    @GetMapping(MappingUtils.MARGIN_WITHDRAW)
    public RestResult marginWithdraw() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return sysCashDetailService.marginWithdraw(cid);
    }

}
