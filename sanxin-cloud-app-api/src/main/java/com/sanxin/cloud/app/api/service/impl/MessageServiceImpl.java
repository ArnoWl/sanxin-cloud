package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.MessageService;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.service.AAdvertContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 申请Service实现类
 * @author xiaoky
 * @date 2019-09-16
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private AAdvertContentService advertContentService;

    @Override
    public void queryAdvertList(SPage<AAdvertContent> page) {
        QueryWrapper<AAdvertContent> wrapper = new QueryWrapper<>();
        // 设置排序
        wrapper.orderByAsc("sort");
        wrapper.orderByDesc("create_time");
        wrapper.eq("status", StaticUtils.STATUS_YES);
        advertContentService.page(page, wrapper);
        for (AAdvertContent a : page.getRecords()) {
            a.setContent(null);
            a.setTitle(null);
        }
    }
}
