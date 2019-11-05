package com.sanxin.cloud.app.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanxin.cloud.app.api.service.MessageService;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.AAdvertContentVO;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.entity.AAdvertFind;
import com.sanxin.cloud.mapper.AAdvertFindMapper;
import com.sanxin.cloud.service.AAdvertContentService;
import com.sanxin.cloud.service.AAdvertFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 申请Service实现类
 *
 * @author xiaoky
 * @date 2019-09-16
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private AAdvertContentService advertContentService;
    @Autowired
    private AAdvertFindService advertFindService;

    @Override
    public RestResult queryAdvertList(SPage<AAdvertFind> page) {
        AAdvertContentVO contentVO = new AAdvertContentVO();
        List<AAdvertFind> transverse = advertFindService.list(new QueryWrapper<AAdvertFind>().eq("type", 0).eq("status", 1).orderByAsc("sort"));
        List<AAdvertFind> banner = advertFindService.list(new QueryWrapper<AAdvertFind>().eq("type", 2).eq("status", 1).orderByAsc("sort"));
        QueryWrapper<AAdvertFind> wrapper = new QueryWrapper<>();
        contentVO.setTransverse(transverse);
        // 设置排序
        wrapper.orderByAsc("sort");
        wrapper.orderByDesc("create_time");
        wrapper.eq("status", StaticUtils.STATUS_YES);
        wrapper.eq("type", 1);
        advertFindService.page(page, wrapper);
        contentVO.setPortrait(page);
        contentVO.setBanner(banner);
        return RestResult.success(contentVO);
    }

    /**
     * 首页广告弹窗
     *
     * @return
     */
    @Override
    public RestResult queryHomeAdvert() {
        AAdvertContent advertContent = null;
        String language = LanguageUtils.getLanguage().toUpperCase();
        try {
            advertContent = advertContentService.getOne(new QueryWrapper<AAdvertContent>().eq("status", 1).eq("home_show", 1));
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.fail("fail");
        }
        if (advertContent != null) {
            String getTitle = advertContent.getTitle();
            JSONObject titleJson = JSONObject.parseObject(getTitle);
            String title = titleJson.getString(language);
            advertContent.setTitle(title);

            String getContent = advertContent.getContent();
            JSONObject contentJson = JSONObject.parseObject(getContent);
            String content = contentJson.getString(language);
            advertContent.setContent(content);
            return RestResult.success(advertContent);

        }
        return RestResult.fail("fail");
    }
}
