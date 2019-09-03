package com.sanxin.cloud.admin.api.controller;

import com.netflix.discovery.converters.Auto;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysAgreement;
import com.sanxin.cloud.entity.SysRichText;
import com.sanxin.cloud.enums.RichTextEnums;
import com.sanxin.cloud.service.SysAgreementService;
import com.sanxin.cloud.service.SysRichTextService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统数据处理Controller
 * @author xiaoky
 * @date 2019-08-31
 */
@RestController
@RequestMapping(value = "/system")
public class SysController {
    @Autowired
    private SysAgreementService sysAgreementService;
    @Autowired
    private SysRichTextService sysRichTextService;

    /**
     * 查询系统协议列表
     * @return
     */
    @GetMapping(value = "/queryAgreementList")
    public RestResult queryAgreementList() {
        List<SysAgreement> list = sysAgreementService.list();
        return RestResult.success("", list);
    }

    /**
     * 查询系统协议详情
     * @param id
     * @return
     */
    @GetMapping(value = "/getAgreementDetail")
    public RestResult getAgreementDetail(Integer id) {
        SysAgreement agreement = sysAgreementService.getById(id);
        return RestResult.success("", agreement);
    }

    /**
     * 编辑协议
     * @param id
     * @param title 标题
     * @param content 内容
     * @return
     */
    @PostMapping(value = "/updateAgreementDetail")
    public RestResult updateAgreementDetail(Integer id, String title, String content) {
        if (StringUtils.isBlank(title)) {
            return RestResult.fail("The title cannot empty");
        }
        if (StringUtils.isBlank(content)) {
            return RestResult.fail("The content cannot empty");
        }
        SysAgreement sysAgreement = new SysAgreement();
        sysAgreement.setId(id);
        sysAgreement.setTitle(title);
        sysAgreement.setContent(content);
        boolean result = sysAgreementService.updateById(sysAgreement);
        if (!result) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }

    /**
     * 查询使用指南列表
     * @return
     */
    @GetMapping(value = "/queryGuideList")
    public RestResult queryGuideList() {
        SysRichText use = sysRichTextService.getByType(RichTextEnums.USE_BATTERY.getType());
        SysRichText returnBat = sysRichTextService.getByType(RichTextEnums.RETURN_BATTERY.getType());
        List<SysRichText> list = new ArrayList<>();
        list.add(use);
        list.add(returnBat);
        return RestResult.success("", list);
    }

    /**
     * 查询指南详情
     * @param type
     * @return
     */
    @GetMapping(value = "/getGuideDetail")
    public RestResult getGuideDetail(Integer type) {
        SysRichText richText = sysRichTextService.getByType(type);
        return RestResult.success("", richText);
    }

    /**
     * 修改使用指南内容
     * @param type
     * @param content 内容
     * @return
     */
    @PostMapping(value = "/updateGuideDetail")
    public RestResult updateRichDetail(Integer type, String content) {
        RestResult result = sysRichTextService.updateRichTextByType(type, content);
        return result;
    }

    /**
     * 查询关于我们
     * @return
     */
    @GetMapping(value = "/getAboutUs")
    public RestResult getAboutUs() {
        SysRichText aboutUs = sysRichTextService.getByType(RichTextEnums.ABOUT_US.getType());
        return RestResult.success("", aboutUs);
    }

    /**
     * 修改关于我们
     * @param content 内容
     * @return
     */
    @PostMapping(value = "/updateAboutUs")
    public RestResult updateAboutUs(String content) {
        RestResult result = sysRichTextService.updateRichTextByType(RichTextEnums.ABOUT_US.getType(), content);
        return result;
    }
}
