package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysAgreement;
import com.sanxin.cloud.service.SysAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统协议、使用指南、关于我们Controller
 * @author xiaoky
 * @date 2019-09-11
 */
@RestController
@RequestMapping("/agree")
public class AgreeController extends BaseController{
    @Autowired
    private SysAgreementService sysAgreementService;

    /**
     * 用户注册协议
     * @return
     */
    @GetMapping(value = "/registerAgree")
    public RestResult registerAgree() {
        Integer type = 1;
        String language = getLanguage();
        SysAgreement agreement = sysAgreementService.getByTypeAndLanguage(type, language);
        return RestResult.success(LanguageUtils.getMessage("password_empty"), agreement);
    }
}
