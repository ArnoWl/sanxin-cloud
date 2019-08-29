package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.enums.CardTypeEnums;
import com.sanxin.cloud.service.BBusinessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查询加盟商列表
     * @param page 分页数据
     * @param business 查询数据
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @GetMapping(value = "/list")
    public RestResult queryAdvertList(SPage<BBusiness> page, BBusiness business) {
        QueryWrapper<BBusiness> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(business.getNickName())) {
            wrapper.eq("nick_name", business.getNickName());
        }
        if (business.getStatus() != null) {
            wrapper.eq("status", business.getStatus());
        }
        businessService.page(page, wrapper);
        for (BBusiness b : page.getRecords()) {
            b.setCardTypeName(CardTypeEnums.getName(b.getCardType()));
            b.setPassWord(null);
        }
        return RestResult.success("", page);
    }

    /**
     * 查询加盟商详情数据
     * @param id
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @GetMapping(value = "/getBusinessDetail")
    public RestResult getBusinessDetail(Integer id) {
        BBusiness business = businessService.getById(id);
        if (business == null) {
            return RestResult.fail("fail");
        }
        business.setPassWord(null);
        return RestResult.success("", business);
    }

    /**
     * 编辑加盟商
     * @param business 数据
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @PostMapping(value = "/handleEditBusiness")
    public RestResult handleEditBusiness(BBusiness business) {
        business.setPassWord(null);
        Boolean result = businessService.updateById(business);
        if (!result) {
            return RestResult.fail("保存失败");
        }
        return RestResult.success("成功");
    }
}
