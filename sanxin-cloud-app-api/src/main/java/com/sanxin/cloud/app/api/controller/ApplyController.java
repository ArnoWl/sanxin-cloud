package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.service.ApplyService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.entity.AgAgent;
import com.sanxin.cloud.entity.BBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 信息Controller
 * @author xiaoky
 * @date 2019-09-16
 */
@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 处理广告申请
     * @param advert 广告申请消息
     * @return
     */
    @PostMapping("/handleAdvertApply")
    public RestResult handleAdvertApply(AAdvert advert) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        advert.setCid(cid);
        RestResult result = applyService.handleAdvertApply(advert);
        return result;
    }
    /**
     * 处理加盟申请
     * @param business 加盟申请消息
     * @return
     */
    @PostMapping("/handleBusinessApply")
    public RestResult handleBusinessApply(BBusiness business) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        business.setCid(cid);
        RestResult result = applyService.handleBusinessApply(business);
        return result;
    }
    /**
     * 处理代理申请
     * @param agent 代理申请消息
     * @return
     */
    @PostMapping("/handleAgentApply")
    public RestResult handleAgentApply(AgAgent agent) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        agent.setCid(cid);
        RestResult result = applyService.handleAgentApply(agent);
        return result;
    }

}
