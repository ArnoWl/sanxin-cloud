package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.service.InfoService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.service.AAdvertContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 信息Controller
 * @author xiaoky
 * @date 2019-09-16
 */
@RestController
@RequestMapping("/info")
public class InfoController {
    private static Integer i = 0;
    @Autowired
    private InfoService infoService;
    @Autowired
    private AAdvertContentService advertContentService;

    /**
     * 查询广告列表
     * @param page 分页
     * @return
     */
    @GetMapping("/queryAdvertList")
    public RestResult queryAdvertList(SPage<AAdvertContent> page) {
        infoService.queryAdvertList(page);
        return null;
    }

}
