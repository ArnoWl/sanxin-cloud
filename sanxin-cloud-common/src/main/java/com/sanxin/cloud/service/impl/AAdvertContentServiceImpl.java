package com.sanxin.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.enums.LanguageEnums;
import com.sanxin.cloud.mapper.AAdvertContentMapper;
import com.sanxin.cloud.service.AAdvertContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告内容表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-04
 */
@Service
public class AAdvertContentServiceImpl extends ServiceImpl<AAdvertContentMapper, AAdvertContent> implements AAdvertContentService {
    private void parseAdvert(AAdvertContent a) {
        JSONObject titleObject = JSONObject.parseObject(a.getTitle());
        a.setCnTitle(titleObject.getString(LanguageEnums.CN.name()));
        a.setEnTitle(titleObject.getString(LanguageEnums.EN.name()));
        a.setThaiTitle(titleObject.getString(LanguageEnums.THAI.name()));
        JSONObject contentObject = JSONObject.parseObject(a.getContent());
        a.setCnContent(contentObject.getString(LanguageEnums.CN.name()));
        a.setEnContent(contentObject.getString(LanguageEnums.EN.name()));
        a.setThaiContent(contentObject.getString(LanguageEnums.THAI.name()));
    }

    @Override
    public void queryAdvertContentList(SPage<AAdvertContent> page, QueryWrapper<AAdvertContent> wrapper) {
        super.page(page, wrapper);
        for (AAdvertContent a : page.getRecords()) {
            parseAdvert(a);
        }
    }

    /**
     * 通过Id查询数据
     * @param id
     * @return
     */
    @Override
    public AAdvertContent getMsgById(Integer id) {
        AAdvertContent advertContent = super.getById(id);
        parseAdvert(advertContent);
        return advertContent;
    }

    @Override
    public RestResult updateHomeShow(Integer id) {
        // 先把前面设置为弹框的数据取消
        AAdvertContent update = new AAdvertContent();
        update.setHomeShow(StaticUtils.STATUS_NO);
        UpdateWrapper<AAdvertContent> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("home_show", StaticUtils.STATUS_YES);
        super.update(update, updateWrapper);
        // 修改成功，将当前数据设置为首页弹框
        update.setId(id);
        update.setHomeShow(StaticUtils.STATUS_YES);
        boolean result = super.updateById(update);
        if (result) {
            return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
        }
        return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
    }
}
