package com.sanxin.cloud.app.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.app.api.service.MessageService;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.entity.CFeedbackLog;
import com.sanxin.cloud.service.CFeedbackLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息管理Controller
 * @author xiaoky
 * @date 2019-09-11
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private CFeedbackLogService cFeedbackLogService;
    @Autowired
    private MessageService messageService;

    /**
     * 故障反馈
     * @param content 反馈内容
     * @param backUrl 反馈图片 1~3张
  * @return
     */
    @PostMapping(value = "/addFeedbackMessage")
    public RestResult addFeedbackMessage(Integer bid, String content, List<String> backUrl) {
        if (StringUtils.isBlank(content)) {
            return RestResult.fail(LanguageUtils.getMessage("content_empty"));
        }
        if (backUrl == null || backUrl.size() == 0) {
            return RestResult.fail(LanguageUtils.getMessage("img_empty"));
        }
        if (backUrl.size() > 3) {
            return RestResult.fail(LanguageUtils.getMessage("img_to_long"));
        }
        CFeedbackLog feedbackLog = new CFeedbackLog();
        feedbackLog.setBid(bid);
        feedbackLog.setContent(content);
        feedbackLog.setBackUrl(JSONArray.toJSONString(backUrl));
        boolean result = cFeedbackLogService.save(feedbackLog);
        if (result) {
            return RestResult.success(LanguageUtils.getMessage("fail"));
        }
        return RestResult.success(LanguageUtils.getMessage("success"));
    }

    /**
     * 查询广告列表
     * @param page 分页
     * @return
     */
    @RequestMapping("/queryAdvertList")
    public RestResult queryAdvertList(SPage<AAdvertContent> page) {
        messageService.queryAdvertList(page);
        return RestResult.success("", page);
    }
}
