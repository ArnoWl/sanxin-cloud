package com.sanxin.cloud.app.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.app.api.common.BusinessMapping;
import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.BusinessBaseVo;
import com.sanxin.cloud.dto.BusinessDetailVo;
import com.sanxin.cloud.dto.PowerBankListVo;
import com.sanxin.cloud.dto.BusinessHomeVo;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.BMoneyDetail;
import com.sanxin.cloud.service.BBusinessService;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Value("${spring.radius}")
    private Integer radius;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private BBusinessService bBusinessService;
    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 根据经纬度分页查询周边商铺
     * @param current
     * @param size
     * @param latVal
     * @param lonVal
     * @param search
     * @return
     */
    @GetMapping(value = MappingUtils.NRARBY_BUSINESS)
    public RestResult pageByShops(@RequestParam Integer current, @RequestParam Integer size, String latVal, String lonVal,String search){
        IPage<PowerBankListVo> byShops = businessService.findByShops(current, size, latVal, lonVal, search, radius);
        return RestResult.success("成功",byShops);
    }

    /**
     * 获取店铺基本资料
     * @return
     */
    @GetMapping(value = BusinessMapping.GET_BUSINESS_INFO)
    public RestResult getBusinessInfo() {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        BusinessBaseVo vo = businessService.getBusinessInfo(bid);
        return RestResult.success("", vo);
    }

    /**
     * 编辑商家个人中心资料
     * @param vo
     * @return
     */
    @PutMapping(value = BusinessMapping.EDIT_BUSINESS_INFO)
    public RestResult editBusinessInfo(BusinessBaseVo vo) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        BBusiness business = bBusinessService.validById(bid);
        // 校验参数
        if (StringUtils.isBlank(vo.getHeadUrl())) {
            return RestResult.fail("business_headurl_empty");
        }
        if (StringUtils.isBlank(vo.getNickName())) {
            return RestResult.fail("business_nickname_empty");
        }
        if (vo.getCardType() == null) {
            return RestResult.fail("business_cardtype_empty");
        }
        if (StringUtils.isBlank(vo.getCardNo())) {
            return RestResult.fail("business_cardno_empty");
        }
        // 赋值修改
        business.setHeadUrl(vo.getHeadUrl());
        business.setNickName(vo.getNickName());
        business.setCardType(vo.getCardType());
        business.setCardNo(vo.getCardNo());
        boolean result = bBusinessService.updateById(business);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 获取商家中心数据
     * @return
     */
    @GetMapping(value = BusinessMapping.GET_BUSINESS_CENTER)
    public RestResult getBusinessCenter() {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        BusinessDetailVo vo = businessService.getBusinessCenter(bid);
        return RestResult.success("", vo);
    }

    /**
     * 编辑商家中心
     * @param vo
     * @return
     */
    @PutMapping(value = BusinessMapping.EDIT_BUSINESS_CENTER)
    public RestResult editBusinessCenter(BusinessDetailVo vo) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        RestResult result = businessService.editBusinessCenter(bid, vo);
        return result;
    }

    /**
     * 查询加盟商首页数据
     * @return
     */
    @GetMapping(value = BusinessMapping.GET_BUSINESS_HOME)
    public RestResult getBusinessHome() {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        BusinessHomeVo vo = businessService.getBusinessHome(bid);
        return RestResult.success("", vo);
    }

    /**
     * 查询店铺余额明细
     * @param page
     * @return
     */
    @GetMapping(value = BusinessMapping.QUERY_MONEY_DETAIL_LIST)
    public RestResult queryMoneyDetailList(SPage<BMoneyDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        BMoneyDetail detail = new BMoneyDetail();
        detail.setBid(bid);
        businessService.queryMoneyDetailList(page, detail);
        return RestResult.success("", page);
    }

    /**
     * 查询收益明细
     * @param page 分页数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GetMapping(value = BusinessMapping.QUERY_INCOME_DETAIL_LIST)
    public RestResult queryIncomeDetailList(SPage<BMoneyDetail> page, String startTime, String endTime) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        BMoneyDetail detail = new BMoneyDetail();
        detail.setBid(bid);
        detail.setIsout(StaticUtils.PAY_IN);
        detail.setStartTime(startTime);
        detail.setEndTime(endTime);
        businessService.queryMoneyDetailList(page, detail);
        return RestResult.success("", page);
    }

    /**
     * 查询收益统计数据
     * @param type
     * @return
     */
    @GetMapping(value = BusinessMapping.QUERY_INCOME_STATISTICS)
    public RestResult queryIncomeStatistics(Integer type) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        Map<String, Object> map = businessService.queryIncomeStatistics(bid, type);
        return RestResult.success("", map);
    }
}
