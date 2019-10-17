package com.sanxin.cloud.app.api.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CFeedbackLog;
import com.sanxin.cloud.entity.CPushLog;
import com.sanxin.cloud.service.CFeedbackLogService;
import com.sanxin.cloud.service.CPushLogService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private CFeedbackLogService feedbackLogService;

    /**
     * 我的消息
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/myMessage")
    public RestResult queryMyMessage(SPage<CPushLog> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        IPage<CPushLog> logIPage = pushLogService.queryMyMessage(page, cid);
        return RestResult.success(logIPage);
    }

    /**
     * 已读消息
     * @param id 消息id
     * @param type 已读类型
     * @return
     */
    @RequestMapping(value = "/readMessage")
    public RestResult readMessage(Integer id, Integer type) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return pushLogService.readMessage(id, type, cid);
    }

    /**
     * 加盟商的反馈列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/faultFeedback")
    public RestResult queryFaultFeedback(SPage<CFeedbackLog> page) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        IPage<CFeedbackLog> feedback = feedbackLogService.queryFaultFeedback(page, bid);
        return RestResult.success(feedback);
    }

}
