package com.sanxin.cloud.app.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.sanxin.cloud.app.api.service.MessageService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.entity.CFeedbackLog;
import com.sanxin.cloud.service.CFeedbackLogService;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 故障反馈
     * @param content 反馈内容
     * @param backUrl 反馈图片 1~3张
  * @return
     */
    @RequestMapping("/addFeedbackMessage")
    public RestResult addFeedbackMessage(String content,@RequestParam(value = "backUrl") List<String> backUrl) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
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
        feedbackLog.setCid(cid);
        feedbackLog.setContent(content);
        feedbackLog.setBackUrl(JSONArray.toJSONString(backUrl));
        boolean result = cFeedbackLogService.save(feedbackLog);
        if (result) {
            return RestResult.success(LanguageUtils.getMessage("success"));
        }
        return RestResult.fail(LanguageUtils.getMessage("fail"));
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
