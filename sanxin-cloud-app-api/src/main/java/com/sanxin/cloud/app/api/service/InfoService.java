package com.sanxin.cloud.app.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.enums.LanguageEnums;
import com.sanxin.cloud.service.AAdvertContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 申请Service
 * @author xiaoky
 * @date 2019-09-16
 */
@Service
public class InfoService {
    @Autowired
    private AAdvertContentService advertContentService;

    public void queryAdvertList(SPage<AAdvertContent> page) {
        QueryWrapper<AAdvertContent> wrapper = new QueryWrapper<>();
        // 设置排序
        wrapper.orderByAsc("sort");
        wrapper.eq("status", StaticUtils.STATUS_YES);
        advertContentService.page(page, wrapper);
        for (AAdvertContent a : page.getRecords()) {
            a.setContent(null);
            a.setTitle(null);
        }
    }
}
