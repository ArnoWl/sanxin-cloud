package com.sanxin.cloud.admin.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BAccount;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.enums.CardTypeEnums;
import com.sanxin.cloud.mapper.BAccountMapper;
import com.sanxin.cloud.service.BBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 加盟商Controller
 *
 * @author xiaoky
 * @date 2019-08-27
 */
@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BBusinessService businessService;
    @Autowired
    private BAccountMapper accountMapper;

    /**
     * 查询加盟商列表
     *
     * @param page     分页数据
     * @param business 查询数据
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @GetMapping(value = "/list")
    public RestResult queryBusinessList(SPage<BBusiness> page, BBusiness business) {
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
     * 查询加盟商数据——不分页
     *
     * @return
     */
    @GetMapping(value = "/queryAllList")
    public RestResult queryAllList() {
        QueryWrapper<BBusiness> wrapper = new QueryWrapper<>();
        wrapper.eq("status", StaticUtils.STATUS_SUCCESS);
        List<BBusiness> list = businessService.list(wrapper);
        for (BBusiness b : list) {
            b.setPassWord(null);
        }
        return RestResult.success("", list);
    }

    /**
     * 查询加盟商详情数据
     *
     * @param id
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @GetMapping(value = "/getBusinessDetail")
    public RestResult getBusinessDetail(Integer id) {
        BBusiness business = businessService.selectById(id);
        return RestResult.success("", business);
    }

    /**
     * 编辑加盟商
     *
     * @param business 数据
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @PostMapping(value = "/handleEditBusiness")
    public RestResult handleEditBusiness(BBusiness business) {
        business.setPassWord(null);
        business.setStatus(null);
        if (business.getCoverUrlList() == null || business.getCoverUrlList().size() <= 0) {
        } else {
            // 将图片重新封装一次
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (String coverUrl : business.getCoverUrlList()) {
                Map<String, Object> map = new HashMap<>();
                map.put("url", coverUrl);
                mapList.add(map);
            }
            business.setCoverUrl(JSON.toJSONString(mapList));
        }
        Boolean result = businessService.updateById(business);
        if (!result) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
    }

    /**
     * 加盟商审核
     *
     * @param id     加盟商id
     * @param status 审核状态
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @PostMapping(value = "/handleStatus")
    public RestResult handleStatus(Integer id, Integer status) {
        BBusiness business = new BBusiness();
        business.setId(id);
        business.setStatus(status);
        business.setCheckTime(new Date());
        BAccount account = new BAccount();
        account.setBid(id);
        int insert = accountMapper.insert(account);
        boolean result = businessService.updateById(business);
        if (result && insert > 0) {
            return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
        }
        return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
    }

    /**
     * 重置登录密码
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/resetLoginPass")
    public RestResult resetLoginPass(Integer id) {
        BBusiness business = businessService.getById(id);
        if (business == null || !FunctionUtils.isEquals(StaticUtils.STATUS_SUCCESS, business.getStatus())) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        business.setPassWord(PwdEncode.encodePwd(StaticUtils.DEFAULT_PWD));
        boolean result = businessService.updateById(business);
        if (result) {
            return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
        }
        return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
    }

    /**
     * 重置支付密码
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/resetPayPass")
    public RestResult resetPayPass(Integer id) {
        BBusiness business = businessService.getById(id);
        if (business == null || !FunctionUtils.isEquals(StaticUtils.STATUS_SUCCESS, business.getStatus())) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        business.setPayWord(PwdEncode.encodePwd(StaticUtils.DEFAULT_PWD));
        boolean result = businessService.updateById(business);
        if (result) {
            return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
        }
        return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
    }
}
