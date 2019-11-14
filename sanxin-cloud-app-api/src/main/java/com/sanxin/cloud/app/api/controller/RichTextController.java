package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysRichText;
import com.sanxin.cloud.service.AddressService;
import com.sanxin.cloud.service.AppStartupService;
import com.sanxin.cloud.service.CCountryMobileService;
import com.sanxin.cloud.service.SysRichTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于我们,使用指南Controller
 * @author zdc
 * @date 2019-09-17
 */
@RestController
@RequestMapping("/richText")
public class RichTextController {

    @Autowired
    private SysRichTextService sysRichTextService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CCountryMobileService countryMobileService;
    @Autowired
    private AppStartupService appStartupService;
    /**
     * 使用充电宝指南
     * @return
     */
    @RequestMapping(value = "/use")
    public RestResult use() {
        Integer type = 1;
        String language = BaseUtil.getLanguage();
        SysRichText richText = sysRichTextService.getByTypeAndLanguage(type, language);
        return RestResult.success(LanguageUtils.getMessage("password_empty"), richText);
    }

    /**
     * 归还充电宝指南
     * @return
     */
    @RequestMapping(value = "/restore")
    public RestResult restore() {
        Integer type = 2;
        String language = BaseUtil.getLanguage();
        SysRichText richText = sysRichTextService.getByTypeAndLanguage(type, language);
        return RestResult.success(LanguageUtils.getMessage("password_empty"), richText);
    }

    /**
     * 关于我们
     * @return
     */
    @RequestMapping(value = "/aboutUs")
    public RestResult aboutUs() {
        Integer type = 3;
        String language = BaseUtil.getLanguage();
        SysRichText richText = sysRichTextService.getByTypeAndLanguage(type, language);
        return RestResult.success(LanguageUtils.getMessage("password_empty"), richText);
    }

    /**
     * 获取地址
     * @return
     */
    @RequestMapping(value = "/areaCode")
    public RestResult getAreaCode() {
        return countryMobileService.getAreaCode();
    }

    /**
     * 省市三级
     * @return
     */
    @RequestMapping(value = "/address")
    public RestResult getAddress(Integer parent) {
        return addressService.getAddress(parent);
    }

    /**
     * 启动图
     * @return
     */
    @RequestMapping(value = "/startupDiagram")
    public RestResult getStartupDiagram() {
        return appStartupService.getStartupDiagram();
    }
}
