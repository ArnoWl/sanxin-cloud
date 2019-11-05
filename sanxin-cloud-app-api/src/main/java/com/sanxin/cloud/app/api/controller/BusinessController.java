package com.sanxin.cloud.app.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.app.api.common.BusinessMapping;
import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.BusinessBaseVo;
import com.sanxin.cloud.dto.BusinessDetailVo;
import com.sanxin.cloud.dto.BusinessHomeVo;
import com.sanxin.cloud.dto.PowerBankListVo;
import com.sanxin.cloud.entity.BAccount;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.BMoneyDetail;
import com.sanxin.cloud.service.BAccountService;
import com.sanxin.cloud.service.BBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @Autowired
    private BAccountService bAccountService;

    /**
     * 根据经纬度分页搜索周边商铺
     * @param current 当前页
     * @param size 每页大小
     * @param latVal 纬度
     * @param lonVal 经度
     * @param search 搜索
     * @return
     */
    @RequestMapping(value = MappingUtils.NRARBY_BUSINESS)
    public RestResult pageByShops(@RequestParam Integer current, @RequestParam Integer size, String latVal, String lonVal,String search){
        IPage<PowerBankListVo> byShops = businessService.findByShops(current, size, latVal, lonVal, search, radius);
        return RestResult.success("success",byShops);
    }

    /**
     * 根据经纬度和范围搜索周边商铺
     * @param latVal 纬度
     * @param lonVal 经度
     * @param distance 范围距离(米)
     * @return
     */
    @RequestMapping(value = MappingUtils.RANGE_SHOP)
    public RestResult rangeShop(String latVal, String lonVal,Integer distance){
        return businessService.rangeShop(latVal, lonVal, distance);
    }

    /**
     * 获取店铺基本资料
     * @return
     */
    @RequestMapping(value = BusinessMapping.GET_BUSINESS_INFO)
    public RestResult getBusinessInfo() {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        BusinessBaseVo vo = businessService.getBusinessInfo(bid);
        return RestResult.success("", vo);
    }

    /**
     * 编辑商家个人中心资料
     * @param vo
     * @return
     */
    @RequestMapping(value = BusinessMapping.EDIT_BUSINESS_INFO)
    public RestResult editBusinessInfo(BusinessBaseVo vo) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
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
    @RequestMapping(value = BusinessMapping.GET_BUSINESS_CENTER)
    public RestResult getBusinessCenter() {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        BusinessDetailVo vo = businessService.getBusinessCenter(bid);
        return RestResult.success("", vo);
    }

    /**
     * 编辑商家中心
     * @param vo
     * @return
     */
    @PostMapping(value = BusinessMapping.EDIT_BUSINESS_CENTER)
    public RestResult editBusinessCenter(BusinessDetailVo vo) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        RestResult result = businessService.editBusinessCenter(bid, vo);
        return result;
    }

    /**
     * 查询加盟商首页数据
     * @return
     */
    @RequestMapping(value = BusinessMapping.GET_BUSINESS_HOME)
    public RestResult getBusinessHome() {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        BusinessHomeVo vo = businessService.getBusinessHome(bid);
        return RestResult.success("", vo);
    }

    /**
     * 查询店铺余额明细
     * @param page
     * @return
     */
    @RequestMapping(value = BusinessMapping.QUERY_MONEY_DETAIL_LIST)
    public RestResult queryMoneyDetailList(SPage<BMoneyDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        BMoneyDetail detail = new BMoneyDetail();
        detail.setBid(bid);
        businessService.queryMoneyDetailList(page, detail);
        // 余额、收益
        BAccount bAccount = bAccountService.getByBid(detail.getBid());
        Map<String, Object> map = new HashMap<>();
        map.put("list", page);
        map.put("money", bAccount.getMoney());
        return RestResult.success("", map);
    }

    /**
     * 查询收益明细
     * @param page 分页数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @RequestMapping(value = BusinessMapping.QUERY_INCOME_DETAIL_LIST)
    public RestResult queryIncomeDetailList(SPage<BMoneyDetail> page, String startTime, String endTime) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        BMoneyDetail detail = new BMoneyDetail();
        detail.setBid(bid);
        detail.setIsout(StaticUtils.PAY_IN);
        detail.setStartTime(startTime);
        detail.setEndTime(endTime);
        businessService.queryMoneyDetailList(page, detail);
        // 余额、收益
        BAccount bAccount = bAccountService.getByBid(detail.getBid());
        Map<String, Object> map = new HashMap<>();
        map.put("list", page);
        map.put("totalIncome", bAccount.getTotalIncome());
        map.put("todayIncome", businessService.getTodayIncome(bid));
        return RestResult.success("", map);
    }

    /**
     * 查询收益统计数据
     * @param type
     * @return
     */
    @RequestMapping(value = BusinessMapping.QUERY_INCOME_STATISTICS)
    public RestResult queryIncomeStatistics(Integer type) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        Map<String, Object> map = businessService.queryIncomeStatistics(bid, type);
        return RestResult.success("", map);
    }

    /**
     * 修改密码
     * @param verCode 验证码
     * @param password 密码
     * @param type 类型
     * @return
     */
    @RequestMapping(value = BusinessMapping.UPDATE_PASSWORD)
    public RestResult updatePassword(String verCode, String password, Integer type) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        RestResult result = businessService.updatePassword(bid, verCode, password, type);
        return result;
    }

    /**
     * 根据code获取商家个人信息
     * @return
     */
    @RequestMapping(value = BusinessMapping.BUSINESS_DETAIL)
    public RestResult businessDetail(String code) {
        RestResult result = businessService.businessDetail(code,radius);
        return result;
    }
}
