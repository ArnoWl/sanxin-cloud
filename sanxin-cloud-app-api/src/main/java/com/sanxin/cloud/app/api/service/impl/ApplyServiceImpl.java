package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.ApplyService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.entity.AgAgent;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.enums.CardTypeEnums;
import com.sanxin.cloud.service.AAdvertService;
import com.sanxin.cloud.service.AgAgentService;
import com.sanxin.cloud.service.BBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 处理申请Service
 * @author xiaoky
 * @date 2019-09-16
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private AAdvertService advertService;
    @Autowired
    private BBusinessService businessService;
    @Autowired
    private AgAgentService agAgentService;

    /**
     * 处理广告申请
     * @param advert
     * @return
     */
    @Override
    public RestResult handleAdvertApply(AAdvert advert) {
        if (advert == null) {
            return RestResult.fail("data_exception");
        }
        // 判断申请状态
        AAdvert queryAdvert = advertService.getByCid(advert.getCid());
        if (queryAdvert != null) {
            // 只有驳回状态才修改信息
            if (FunctionUtils.isEquals(queryAdvert.getStatus(), StaticUtils.STATUS_APPLY)) {
                return RestResult.fail("apply_submit_apply");
            } else if (FunctionUtils.isEquals(queryAdvert.getStatus(), StaticUtils.STATUS_SUCCESS)) {
                return RestResult.fail("apply_submit_success");
            }
            advert.setId(queryAdvert.getId());
        }
        if (StringUtils.isBlank(advert.getRealName())) {
            return RestResult.fail("apply_name_empty");
        }
        if (StringUtils.isBlank(advert.getPhone())) {
            return RestResult.fail("apply_phone_empty");
        }
        // 判断手机号-联系方式是否已经被别人申请使用了
        QueryWrapper<AAdvert> advertWrapper = new QueryWrapper<AAdvert>();
        advertWrapper.eq("phone", advert.getPhone()).ne("id", advert.getId());
        int count = advertService.count(advertWrapper);
        if (count > 0) {
            return RestResult.fail("apply_phone_use");
        }
        if (advert.getProId() == null || advert.getCityId() == null) {
            return RestResult.fail("apply_addr_empty");
        }
        if (StringUtils.isBlank(advert.getAddressDetail())) {
            return RestResult.fail("apply_addr_detail_empty");
        }
        if (StringUtils.isBlank(advert.getCompanyName())) {
            return RestResult.fail("apply_company_name_empty");
        }
        if (StringUtils.isBlank(advert.getLicenseCode())) {
            return RestResult.fail("apply_license_code_empty");
        }
        if (StringUtils.isBlank(advert.getLicenseImg())) {
            return RestResult.fail("apply_license_img_empty");
        }
        if (StringUtils.isBlank(advert.getCompanyImg())) {
            return RestResult.fail("apply_company_img_empty");
        }
        // 提交——保存数据
        advert.setCreateTime(new Date());
        advert.setStatus(StaticUtils.STATUS_APPLY);
        boolean result = advertService.saveOrUpdate(advert);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 处理店铺申请
     * @param business
     * @return
     */
    @Override
    public RestResult handleBusinessApply(BBusiness business) {
        if (business == null) {
            return RestResult.fail("data_exception");
        }
        // 判断申请状态
        BBusiness queryBusiness = businessService.getByCid(business.getCid());
        if (queryBusiness != null) {
            // 只有驳回状态才修改信息
            if (FunctionUtils.isEquals(queryBusiness.getStatus(), StaticUtils.STATUS_APPLY)) {
                return RestResult.fail("apply_submit_apply");
            } else if (FunctionUtils.isEquals(queryBusiness.getStatus(), StaticUtils.STATUS_SUCCESS)) {
                return RestResult.fail("apply_submit_success");
            }
            business.setId(queryBusiness.getId());
        }
        if (StringUtils.isBlank(business.getRealName())) {
            return RestResult.fail("apply_name_empty");
        }
        if (StringUtils.isBlank(business.getPhone())) {
            return RestResult.fail("apply_phone_empty");
        }
        // 判断手机号-联系方式是否已经被别人申请使用了
        QueryWrapper<BBusiness> businessWrapper = new QueryWrapper<BBusiness>();
        businessWrapper.eq("phone", business.getPhone()).ne("id", business.getId());
        int count = businessService.count(businessWrapper);
        if (count > 0) {
            return RestResult.fail("apply_phone_use");
        }
        if (business.getProId() == null || business.getCityId() == null) {
            return RestResult.fail("apply_addr_empty");
        }
        if (StringUtils.isBlank(business.getAddressDetail())) {
            return RestResult.fail("apply_addr_detail_empty");
        }
        // 判断证件信息
        if (business.getCardType() == null) {
            return RestResult.fail("apply_card_type_empty");
        }
        if (StringUtils.isBlank(business.getCardNo())) {
            return RestResult.fail("apply_card_no_empty");
        }
        if (FunctionUtils.isEquals(business.getCardType(), CardTypeEnums.ID_CARD.getType())) {
            if (StringUtils.isBlank(business.getCardFront())) {
                return RestResult.fail("apply_card_front_empty");
            }
            if (StringUtils.isBlank(business.getCardBack())) {
                return RestResult.fail("apply_card_back_empty");
            }
        } else if (FunctionUtils.isEquals(business.getCardType(), CardTypeEnums.PASS_PORT.getType())) {
            return RestResult.fail("apply_card_pass_empty");
        } else {
            return RestResult.fail("data_exception");
        }
        if (StringUtils.isBlank(business.getCompanyName())) {
            return RestResult.fail("apply_company_name_empty");
        }
        if (StringUtils.isBlank(business.getLicenseCode())) {
            return RestResult.fail("apply_license_code_empty");
        }
        if (StringUtils.isBlank(business.getLicenseImg())) {
            return RestResult.fail("apply_license_img_empty");
        }
        if (StringUtils.isBlank(business.getCompanyImg())) {
            return RestResult.fail("apply_company_img_empty");
        }
        // 提交——保存数据
        business.setCreateTime(new Date());
        business.setStatus(StaticUtils.STATUS_APPLY);
        boolean result = businessService.saveOrUpdate(business);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 处理代理申请
     * @param agent
     * @return
     */
    @Override
    public RestResult handleAgentApply(AgAgent agent) {
        if (agent == null) {
            return RestResult.fail("data_exception");
        }
        // 判断申请状态
        AgAgent queryAgent = agAgentService.getByCid(agent.getCid());
        if (queryAgent != null) {
            // 只有驳回状态才修改信息
            if (FunctionUtils.isEquals(queryAgent.getStatus(), StaticUtils.STATUS_APPLY)) {
                return RestResult.fail("apply_submit_apply");
            } else if (FunctionUtils.isEquals(queryAgent.getStatus(), StaticUtils.STATUS_SUCCESS)) {
                return RestResult.fail("apply_submit_success");
            }
            agent.setId(queryAgent.getId());
        }
        if (StringUtils.isBlank(agent.getRealName())) {
            return RestResult.fail("apply_name_empty");
        }
        if (StringUtils.isBlank(agent.getPhone())) {
            return RestResult.fail("apply_phone_empty");
        }
        // 判断手机号-联系方式是否已经被别人申请使用了
        QueryWrapper<AgAgent> agentWrapper = new QueryWrapper<AgAgent>();
        agentWrapper.eq("phone", agent.getPhone()).ne("id", agent.getId());
        int count = agAgentService.count(agentWrapper);
        if (count > 0) {
            return RestResult.fail("apply_phone_use");
        }
        if (agent.getProId() == null || agent.getCityId() == null) {
            return RestResult.fail("apply_addr_empty");
        }
        if (StringUtils.isBlank(agent.getAddressDetail())) {
            return RestResult.fail("apply_addr_detail_empty");
        }
        if (StringUtils.isBlank(agent.getCompanyName())) {
            return RestResult.fail("apply_company_name_empty");
        }
        if (StringUtils.isBlank(agent.getLicenseCode())) {
            return RestResult.fail("apply_license_code_empty");
        }
        if (StringUtils.isBlank(agent.getLicenseImg())) {
            return RestResult.fail("apply_license_img_empty");
        }
        if (StringUtils.isBlank(agent.getCompanyImg())) {
            return RestResult.fail("apply_company_img_empty");
        }
        // 提交——保存数据
        agent.setCreateTime(new Date());
        agent.setStatus(StaticUtils.STATUS_APPLY);
        boolean result = agAgentService.saveOrUpdate(agent);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }
}
