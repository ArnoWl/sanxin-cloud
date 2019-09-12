package com.sanxin.cloud.admin.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.dto.LanguageVo;
import com.sanxin.cloud.entity.GiftHour;
import com.sanxin.cloud.entity.SysAgreement;
import com.sanxin.cloud.entity.SysRichText;
import com.sanxin.cloud.enums.LanguageEnums;
import com.sanxin.cloud.enums.RichTextEnums;
import com.sanxin.cloud.service.GiftHourService;
import com.sanxin.cloud.service.SysAgreementService;
import com.sanxin.cloud.service.SysRichTextService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统数据处理Controller
 * @author xiaoky
 * @date 2019-08-31
 */
@RestController
@RequestMapping(value = "/system")
public class SysController extends BaseController {
    @Autowired
    private SysAgreementService sysAgreementService;
    @Autowired
    private SysRichTextService sysRichTextService;
    @Autowired
    private GiftHourService giftHourService;

    /**
     * 查询系统协议列表
     * @return
     */
    @GetMapping(value = "/queryAgreementList")
    public RestResult queryAgreementList() {
        List<SysAgreement> list = sysAgreementService.list();
        for (SysAgreement l : list) {
            JSONObject object = JSONObject.parseObject(l.getTitle());
            l.setCnTitle(object.getString(LanguageEnums.CN.name()));
            l.setEnTitle(object.getString(LanguageEnums.EN.name()));
            l.setThaiTitle(object.getString(LanguageEnums.THAI.name()));
            l.setTitle(null);
        }
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
        if (agreement != null) {
            JSONObject object = JSONObject.parseObject(agreement.getTitle());
            agreement.setCnTitle(object.getString(LanguageEnums.CN.name()));
            agreement.setEnTitle(object.getString(LanguageEnums.EN.name()));
            agreement.setThaiTitle(object.getString(LanguageEnums.THAI.name()));
            agreement.setTitle(null);
        }
        return RestResult.success("", agreement);
    }

    /**
     * 编辑协议
     * @param agreement
     * @return
     */
    @PostMapping(value = "/updateAgreementDetail")
    public RestResult updateAgreementDetail(SysAgreement agreement) {
        if (StringUtils.isBlank(agreement.getCnTitle()) || StringUtils.isBlank(agreement.getEnTitle())
                || StringUtils.isBlank(agreement.getThaiTitle())) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.SYSTEM_AGREE_TITLE));
        }
        if (StringUtils.isBlank(agreement.getCnContent()) || StringUtils.isBlank(agreement.getEnContent())
                || StringUtils.isBlank(agreement.getThaiContent())) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.SYSTEM_AGREE_CONTENT));
        }
        LanguageVo languageVo = new LanguageVo(agreement.getCnTitle(), agreement.getEnTitle(), agreement.getThaiTitle());
        agreement.setTitle(JSON.toJSONString(languageVo));
        boolean result = sysAgreementService.updateById(agreement);
        if (!result) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
    }

    /**
     * 查询使用指南列表
     * @return
     */
    @GetMapping(value = "/queryGuideList")
    public RestResult queryGuideList() {
        SysRichText use = sysRichTextService.getByType(RichTextEnums.USE_BATTERY.getType(), getLanguage());
        SysRichText returnBat = sysRichTextService.getByType(RichTextEnums.RETURN_BATTERY.getType(), getLanguage());
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
        SysRichText richText = sysRichTextService.getByType(type, getLanguage());
        return RestResult.success("", richText);
    }

    /**
     * 修改使用指南内容
     * @param type
     * @param cnContent 中文内容
     * @param enContent 英文
     * @param thaiContent 泰文内容
     * @return
     */
    @PostMapping(value = "/updateGuideDetail")
    public RestResult updateRichDetail(Integer type, String cnContent, String enContent, String thaiContent) {
        SysRichText richText = new SysRichText();
        richText.setType(type);
        richText.setCnContent(cnContent);
        richText.setEnContent(enContent);
        richText.setThaiContent(thaiContent);
        RestResult result = sysRichTextService.updateRichTextByType(richText);
        return result;
    }

    /**
     * 查询关于我们
     * @return
     */
    @GetMapping(value = "/getAboutUs")
    public RestResult getAboutUs() {
        SysRichText aboutUs = sysRichTextService.getByType(RichTextEnums.ABOUT_US.getType(), getLanguage());
        return RestResult.success("", aboutUs);
    }

    /**
     * 修改关于我们
     * @param cnContent 中文内容
     * @param enContent 英文内容
     * @param thaiContent 泰文内容
     * @return
     */
    @PostMapping(value = "/updateAboutUs")
    public RestResult updateAboutUs(String cnContent, String enContent, String thaiContent) {
        SysRichText richText = new SysRichText();
        richText.setType(RichTextEnums.ABOUT_US.getType());
        richText.setCnContent(cnContent);
        richText.setEnContent(enContent);
        richText.setThaiContent(thaiContent);
        RestResult result = sysRichTextService.updateRichTextByType(richText);
        return result;
    }

    /**
     * 时长礼包列表
     * @return
     */
    @GetMapping(value = "/queryGiftHourList")
    public RestResult queryGiftHourList() {
        List<GiftHour> list = giftHourService.list();
        return RestResult.success("", list);
    }

    @DeleteMapping("/deleteGiftHour")
    public RestResult deleteGiftHour(Integer id) {
        boolean result = giftHourService.removeById(id);
        if (!result) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
    }

    @PostMapping("/updateGiftHour")
    public RestResult updateGiftHour(GiftHour hour) {
        if (hour.getMoney() == null) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.SYSTEM_GIFT_MONEY));
        }
        if (hour.getHour() == null) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.SYSTEM_GIFT_HOUR));
        }
        if (BigDecimal.ZERO.compareTo(hour.getMoney())>=0 || BigDecimal.ZERO.compareTo(hour.getHour())>=0) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.SYSTEM_GIFT_NUM));
        }
        BigDecimal discountMoney = FunctionUtils.sub(hour.getHour(), hour.getMoney(), 2);
        hour.setDiscountMoney(discountMoney);
        boolean result = giftHourService.updateById(hour);
        if (!result) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
    }
}
