package com.sanxin.cloud.admin.api.controller;

import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.InfoAli;
import com.sanxin.cloud.service.InfoAliService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 支付管理Controller
 * @author xiaoky
 * @date 2019-09-09
 */
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {
    @Autowired
    private InfoAliService infoAliService;

    /**
     * 获取支付宝配置信息
     * @return
     */
    @GetMapping(value = "/getAliPayDetail")
    public RestResult getAliPayDetail() {
        InfoAli infoAli = infoAliService.getInfoAli();
        infoAli.setAliName(null);
        infoAli.setBody(null);
        infoAli.setSubject(null);
        infoAli.setCert(null);
        infoAli.setRefundApp(null);
        infoAli.setRefundWeb(null);
        return RestResult.success("", infoAli);
    }

    /**
     * 编辑支付宝支付信息
     * @param infoAli
     * @return
     */
    @PostMapping(value = "/editAliPay")
    public RestResult editAliPay(InfoAli infoAli) {
        infoAli.setAliName(null);
        infoAli.setBody(null);
        infoAli.setSubject(null);
        infoAli.setCert(null);
        infoAli.setRefundApp(null);
        infoAli.setRefundWeb(null);
        if (StringUtils.isBlank(infoAli.getPartner())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_PARTNER);
        }
        if (StringUtils.isBlank(infoAli.getPartnerAccount())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_PARTNER_ACCOUNT);
        }
        if (StringUtils.isBlank(infoAli.getPrivateKey())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_PRIVATE_KEY);
        }
        if (StringUtils.isBlank(infoAli.getAliPublicKey())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_ALI_PUBLIC_KEY);
        }
        if (StringUtils.isBlank(infoAli.getAliPrivateKey())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_ALI_PRIVATE_KEY);
        }
        if (StringUtils.isBlank(infoAli.getReturnUrl())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_RETURNURL);
        }
        if (StringUtils.isBlank(infoAli.getRefundPublicKey())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_REFUND_PUBLIC_KEY);
        }
        if (StringUtils.isBlank(infoAli.getRefundPrivateKey())) {
            return RestResult.fail(AdminLanguageStatic.P_ALI_REFUND_PRIVATE_KEY);
        }
        boolean result = infoAliService.updateById(infoAli);
        if (result) {
            return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
        }
        return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
    }
}
