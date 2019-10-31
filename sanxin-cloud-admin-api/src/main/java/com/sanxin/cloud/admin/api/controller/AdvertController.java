package com.sanxin.cloud.admin.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.LanguageVo;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.entity.AAdvertFind;
import com.sanxin.cloud.enums.EventEnums;
import com.sanxin.cloud.service.AAdvertContentService;
import com.sanxin.cloud.service.AAdvertFindService;
import com.sanxin.cloud.service.AAdvertService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 广告Controller
 *
 * @author xiaoky
 * @date 2019-08-26
 */
@RestController
@RequestMapping("/advert")
public class AdvertController {
    @Autowired
    private AAdvertService advertService;
    @Autowired
    private AAdvertContentService advertContentService;
    @Autowired
    private AAdvertFindService advertFindService;

    /**
     * 查询广告申请
     *
     * @param page   分页数据
     * @param advert 查询数据
     * @return 分页数据
     */
    @GetMapping(value = "/list")
    public RestResult queryAdvertList(SPage<AAdvert> page, AAdvert advert) {
        QueryWrapper<AAdvert> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(advert.getRealName())) {
            wrapper.eq("nick_name", advert.getRealName());
        }
        if (advert.getStatus() != null) {
            wrapper.eq("status", advert.getStatus());
        }
        advertService.page(page, wrapper);
        return RestResult.success("", page);
    }

    /**
     * 处理广告申请通过驳回
     *
     * @param status 状态
     * @param id     数据ID
     * @return 操作结果
     */
    @PostMapping(value = "/handleStatus")
    public RestResult handleStatus(Integer status, Integer id) {
        AAdvert advert = new AAdvert();
        advert.setStatus(status);
        advert.setId(id);
        advert.setCheckTime(new Date());
        boolean result = advertService.updateById(advert);
        if (result) {
            return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
        }
        return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
    }

    /**
     * 查询广告申请详情
     *
     * @param id
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @GetMapping(value = "/getAdvertDetail")
    public RestResult getAdvertDetail(Integer id) {
        AAdvert advert = advertService.getById(id);
        return RestResult.success("", advert);
    }

    /**
     * 查询广告列表(首页)
     *
     * @param page
     * @param advertContent
     * @return
     */
    @GetMapping("/queryAdvertContentList")
    public RestResult queryAdvertContentList(SPage<AAdvertContent> page, AAdvertContent advertContent) {
        QueryWrapper<AAdvertContent> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(advertContent.getTitle())) {
            wrapper.like("title", advertContent.getTitle());
        }
        if (StringUtils.isNotBlank(advertContent.getContent())) {
            wrapper.like("content", advertContent.getContent());
        }
        if (StringUtils.isNotBlank(advertContent.getEvent())) {
            wrapper.like("event", advertContent.getEvent());
        }
        wrapper.orderByDesc("create_time");
        advertContentService.queryAdvertContentList(page, wrapper);
        return RestResult.success("", page);
    }

    /**
     * 查询广告详情(首页)
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getAdvertContentDetail")
    public RestResult getAdvertContentDetail(Integer id) {
        AAdvertContent advertContent = advertContentService.getMsgById(id);
        return RestResult.success("", advertContent);
    }

    /**
     * 编辑/新增广告(首页)
     *
     * @param advertContent
     * @return
     */
    @PostMapping(value = "/handleEditAdvertContent")
    public RestResult handleEditAdvertContent(AAdvertContent advertContent) {
        if (StringUtils.isBlank(advertContent.getCnTitle())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_CN_TITLE);
        }
        if (StringUtils.isBlank(advertContent.getEnTitle())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_EN_TITLE);
        }
        if (StringUtils.isBlank(advertContent.getThaiTitle())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_THAI_TITLE);
        }
        if (StringUtils.isBlank(advertContent.getCnContent())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_CN_CONTENT);
        }
        if (StringUtils.isBlank(advertContent.getEnContent())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_EN_CONTENT);
        }
        if (StringUtils.isBlank(advertContent.getThaiContent())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_THAI_CONTENT);
        }
        if (StringUtils.isBlank(advertContent.getEvent())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_EVENT_TYPE);
        }
        if (EventEnums.EXTERNAL_LINK.getUrl().equals(advertContent.getEvent())
                && StringUtils.isBlank(advertContent.getUrl())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_EXTERNAL_LINK);
        }
        if (advertContent.getId() == null && StringUtils.isBlank(advertContent.getFrameImg())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_FRAME_IMG);
        }
        /*if (advertContent.getId() == null && StringUtils.isBlank(advertContent.getImg())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_IMG));
        }
        if (advertContent.getSort() == null) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_SORT));
        }*/
        LanguageVo titleVo = new LanguageVo(advertContent.getCnTitle(), advertContent.getEnTitle(), advertContent.getThaiTitle());
        String titleObj = JSONObject.toJSONString(titleVo);
        advertContent.setTitle(titleObj);
        LanguageVo contentVo = new LanguageVo(advertContent.getCnContent(), advertContent.getEnContent(), advertContent.getThaiContent());
        String contentObj = JSONObject.toJSONString(contentVo);
        advertContent.setContent(contentObj);
        boolean result = advertContentService.saveOrUpdate(advertContent);
        if (!result) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
    }

    /**
     * 操作广告状态(首页)
     *
     * @param id
     * @param status 状态
     * @return
     */
    @PostMapping(value = "/handleAdvertContentStatus")
    public RestResult handleAdvertContentStatus(Integer id, Integer status) {
        AAdvertContent advert = advertContentService.getById(id);
        if (status != null && FunctionUtils.isEquals(advert.getStatus(), status)) {
            return RestResult.fail(AdminLanguageStatic.BASE_REPEAT_SUBMIT);
        }
        advert.setStatus(status);
        boolean result = advertContentService.updateById(advert);
        if (!result) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
    }

    /**
     * 设置当前广告为首页弹窗(首页)
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/handleAdvertContentHomeShow")
    public RestResult handleAdvertContentHomeShow(Integer id) {
        AAdvertContent advertContent = advertContentService.getById(id);
        if (FunctionUtils.isEquals(advertContent.getStatus(), StaticUtils.STATUS_NO)) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        if (FunctionUtils.isEquals(advertContent.getHomeShow(), StaticUtils.STATUS_YES)) {
            return RestResult.fail(AdminLanguageStatic.BASE_REPEAT_SUBMIT);
        }
        RestResult result = advertContentService.updateHomeShow(id);
        return result;
    }

    //**********************************************************************************

    /**
     * 查询广告列表(发现)
     *
     * @param page
     * @return
     */
    @GetMapping("/queryAdvertFindList")
    public RestResult queryAdvertFindList(SPage<AAdvertFind> page) {
        advertFindService.queryAdvertFindList(page);
        return RestResult.success("", page);
    }

    /**
     * 查询广告详情(发现)
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getAdvertFindDetail")
    public RestResult getAdvertFindDetail(Integer id) {
        AAdvertFind aAdvertFind = advertFindService.getById(id);
        return RestResult.success("", aAdvertFind);
    }

    /**
     * 编辑/新增广告(发现)
     *
     * @param advertFind
     * @return
     */
    @PostMapping(value = "/handleEditAdvertFind")
    public RestResult handleEditAdvertFind(AAdvertFind advertFind) {
        if (StringUtils.isBlank(advertFind.getImg())) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_IMG);
        }
        if (advertFind.getSort() == null) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_SORT);
        }
        if (advertFind.getType() == null) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_TYPE);
        }
        if (advertFind.getEvent() == null) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_EVENT);
        }
        if (advertFind.getStatus() == null) {
            return RestResult.fail(AdminLanguageStatic.ADVERT_STATUS);
        }
        boolean result = advertFindService.saveOrUpdate(advertFind);
        if (!result) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
    }

    /**
     * 操作广告状态(发现)
     *
     * @param id
     * @param status 状态
     * @return
     */
    @PostMapping(value = "/handleAdvertFindStatus")
    public RestResult handleAdvertFindStatus(Integer id, Integer status) {
        AAdvertFind advert = advertFindService.getById(id);
        if (status != null && FunctionUtils.isEquals(advert.getStatus(), status)) {
            return RestResult.fail(AdminLanguageStatic.BASE_REPEAT_SUBMIT);
        }
        advert.setStatus(status);
        boolean result = advertFindService.updateById(advert);
        if (!result) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
    }
}
