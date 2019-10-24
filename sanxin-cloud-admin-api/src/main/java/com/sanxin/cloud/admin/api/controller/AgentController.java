package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AgAgent;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.service.AgAgentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 代理Controller
 * @author xiaoky
 * @date 2019-08-29
 */
@RestController
@RequestMapping(value = "/agent")
public class AgentController {

    @Autowired
    private AgAgentService agentService;

    /**
     * 查询广告申请
     * @param page 分页数据
     * @param agent 查询数据
     * @return 分页数据
     */
    @GetMapping(value = "/list")
    public RestResult queryAgentList(SPage<AgAgent> page, AgAgent agent) {
        QueryWrapper<AgAgent> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(agent.getRealName())) {
            wrapper.eq("nick_name", agent.getRealName());
        }
        if (agent.getStatus() != null) {
            wrapper.eq("status", agent.getStatus());
        }
        agentService.page(page, wrapper);
        page.getRecords().stream().forEach(a ->a.setPassWord(null));
        return  RestResult.success("", page);
    }

    /**
     * 查询代理申请详情
     * @param id
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @GetMapping(value = "/getAgentDetail")
    public RestResult getAgentDetail(Integer id) {
        AgAgent agent = agentService.getById(id);
        if (agent != null) {
            agent.setPassWord(null);
        }
        return RestResult.success("", agent);
    }

    /**
     * 处理广告申请通过驳回
     * @param status 状态
     * @param id 数据ID
     * @return 操作结果
     */
    @PostMapping(value = "/handleStatus")
    public RestResult handleStatus(Integer status, Integer id, String passWord) {
        if (StringUtils.isBlank(passWord)) {
            return RestResult.fail(AdminLanguageStatic.AGENT_PASSWORD);
        }
        AgAgent agent = new AgAgent();
        agent.setStatus(status);
        agent.setId(id);
        agent.setPassWord(PwdEncode.encodePwd(passWord));
        agent.setCheckTime(new Date());
        boolean result = agentService.updateById(agent);
        if (result) {
            return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
        }
        return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
    }

    /**
     * 重置代理登录密码
     * @param id
     * @return
     */
    @PostMapping(value = "/resetLoginPass")
    public RestResult resetLoginPass(Integer id) {
        AgAgent agent = agentService.getById(id);
        if (agent == null || !FunctionUtils.isEquals(StaticUtils.STATUS_SUCCESS, agent.getStatus())) {
            return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
        }
        agent.setPassWord(PwdEncode.encodePwd(StaticUtils.DEFAULT_PWD));
        boolean result = agentService.updateById(agent);
        if (result) {
            return RestResult.success(AdminLanguageStatic.BASE_SUCCESS);
        }
        return RestResult.fail(AdminLanguageStatic.BASE_FAIL);
    }
}
