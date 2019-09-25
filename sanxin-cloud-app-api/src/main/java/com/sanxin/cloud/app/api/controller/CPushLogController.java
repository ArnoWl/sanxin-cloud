package com.sanxin.cloud.app.api.controller;


import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CPushLog;
import com.sanxin.cloud.service.CPushLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author Arno
 * @since 2019-09-17
 */
@RestController
@RequestMapping("/message")
public class CPushLogController {
    @Autowired
    private CPushLogService pushLogService;
    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 我的消息
     * @param page
     * @return
     */
    @RequestMapping(value = "/myMessage")
    public RestResult queryMyMessage(SPage<CPushLog> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        pushLogService.queryMyMessage(page,Integer.parseInt(token));
        return null;
    }
}
