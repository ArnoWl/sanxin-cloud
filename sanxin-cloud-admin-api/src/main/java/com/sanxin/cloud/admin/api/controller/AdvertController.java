package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.service.AAdvertService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 广告Controller
 * @author xiaoky
 * @date 2019-08-26
 */
@RestController
@RequestMapping("/advert")
public class AdvertController {
    @Autowired
    private AAdvertService advertService;

    /**
     * 查询广告申请
     * @param page 分页数据
     * @param advert 查询数据
     * @return 分页数据
     */
    @GetMapping(value = "/list")
    public RestResult queryAdvertList(Page<AAdvert> page, AAdvert advert) {
        QueryWrapper<AAdvert> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(advert.getNickName())) {
            wrapper.eq("nick_name", advert.getNickName());
        }
        if (advert.getStatus() != null) {
            wrapper.eq("status", advert.getStatus());
        }
        advertService.page(page, wrapper);
        return  RestResult.success("", page);
    }

    /**
     * 处理广告申请通过驳回
     * @param status 状态
     * @param id 数据ID
     * @return 操作结果
     */
    @PostMapping(value = "/handleStatus")
    public RestResult handleStatus(Integer status, Integer id) {
        AAdvert advert = new AAdvert();
        advert.setStatus(status);
        advert.setId(id);
        advert.setCheckTime(new Date());
        boolean result = advertService.updateById(advert);
        if (result) {
            return RestResult.success("操作成功");
        }
        return RestResult.success("操作失败");
    }
}
