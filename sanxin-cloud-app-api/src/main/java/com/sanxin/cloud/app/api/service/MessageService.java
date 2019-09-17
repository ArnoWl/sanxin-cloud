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
 * 申请
 * @author xiaoky
 * @date 2019-09-16
 */
public interface MessageService {

    /**
     * 查询广告列表
     * @param page
     */
    public void queryAdvertList(SPage<AAdvertContent> page);
}
