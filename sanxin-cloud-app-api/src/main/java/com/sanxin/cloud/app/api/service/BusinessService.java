package com.sanxin.cloud.app.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.BusinessBaseVo;
import com.sanxin.cloud.dto.BusinessDetailVo;
import com.sanxin.cloud.dto.BusinessHomeVo;
import com.sanxin.cloud.dto.PowerBankListVo;
import com.sanxin.cloud.entity.BMoneyDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BusinessService {

    /**
     * 根据经纬度分页查询周边商铺
     * @param current
     * @param size
     * @param latVal
     * @param lonVal
     * @param search
     * @param radius
     * @return
     */
    IPage<PowerBankListVo> findByShops(Integer current, Integer size, String latVal, String lonVal, String search, Integer radius);

    /**
     * 根据经纬度搜索所有商铺
     * @param latVal
     * @param lonVal
     * @return
     */
    RestResult rangeShop(String latVal, String lonVal);


    /**
     * 获取商家个人资料信息
     * @param bid
     * @return
     */
    BusinessBaseVo getBusinessInfo(Integer bid);

    /**
     * 查询商家中心数据
     * @param bid
     * @return
     */
    BusinessDetailVo getBusinessCenter(Integer bid);

    /**
     * 编辑商家中心
     * @param bid
     * @param vo
     * @return
     */
    RestResult editBusinessCenter(Integer bid, BusinessDetailVo vo);

    /**
     * 查询商家首页数据
     * @param bid
     * @return
     */
    BusinessHomeVo getBusinessHome(Integer bid);

    /**
     * 查询店铺余额明细
     * @param page
     * @detail 查询信息
     * @return
     */
    void queryMoneyDetailList(SPage<BMoneyDetail> page, BMoneyDetail detail);

    /**
     * 加盟商首页收益统计数据
     * @param bid
     * @param type 类型-区分月、周
     * @return
     */
    Map<String, Object> queryIncomeStatistics(Integer bid, Integer type);

    /**
     * 加盟商修改密码操作
     * @param bid
     * @param verCode 验证码
     * @param password 密码
     * @param type 类型-支付密码|登录密码
     * @return
     */
    RestResult updatePassword(Integer bid, String verCode, String password, Integer type);

    /**
     * 查询今日收益
     * @param bid
     * @return
     */
    BigDecimal getTodayIncome(Integer bid);

    /**
     * 根据code获取商家个人信息
     * @param code
     * @param radius
     * @return
     */
    RestResult businessDetail(String code,Integer radius);
}
