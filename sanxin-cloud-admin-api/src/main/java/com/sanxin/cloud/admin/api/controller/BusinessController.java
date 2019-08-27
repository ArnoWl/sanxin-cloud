package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.service.BBusinessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加盟商Controller
 * @author xiaoky
 * @date 2019-08-27
 */
@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BBusinessService businessService;

    @GetMapping(value = "/list")
    public RestResult queryAdvertList(Page<BBusiness> page, BBusiness business) {
        QueryWrapper<BBusiness> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(business.getNickName())) {
            wrapper.eq("nick_name", business.getNickName());
        }
        if (business.getStatus() != null) {
            wrapper.eq("status", business.getStatus());
        }
        businessService.page(page, wrapper);
        return RestResult.success("", page);
    }
}
