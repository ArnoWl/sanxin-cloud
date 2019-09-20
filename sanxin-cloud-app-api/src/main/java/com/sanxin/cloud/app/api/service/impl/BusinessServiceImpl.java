package com.sanxin.cloud.app.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.*;
import com.sanxin.cloud.entity.BAccount;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.BMoneyDetail;
import com.sanxin.cloud.entity.CPushLog;
import com.sanxin.cloud.enums.CardTypeEnums;
import com.sanxin.cloud.enums.CashTypeEnums;
import com.sanxin.cloud.enums.HandleTypeEnums;
import com.sanxin.cloud.enums.WeekEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.BBusinessMapper;
import com.sanxin.cloud.mapper.BMoneyDetailMapper;
import com.sanxin.cloud.service.BAccountService;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.CPushLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BusinessServiceImpl extends ServiceImpl<BBusinessMapper, BBusiness> implements BusinessService {
    @Autowired
    private BBusinessService businessService;
    @Autowired
    private CPushLogService pushLogService;
    @Autowired
    private BAccountService bAccountService;
    @Autowired
    private BMoneyDetailMapper bMoneyDetailMapper;

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
    @Override
    public IPage<PowerBankListVo> findByShops(Integer current, Integer size, String latVal, String lonVal, String search, Integer radius) {
        IPage<PowerBankListVo> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        List<PowerBankListVo> byShops = baseMapper.findByShops(page, latVal, lonVal, search, radius);
        for (PowerBankListVo byShop : byShops) {
            if (byShop.getLendPort() == 0 && byShop.getRepayPort() != 0) {
                byShop.setStrLendPort("不可租用");
                byShop.setStrRepayPort("可归还");
                byShop.setRemark("温馨提示：无法出借充电宝，请选择其他门店");
            }
            if (byShop.getLendPort() != 0 && byShop.getRepayPort() == 0) {
                byShop.setStrLendPort("可租用");
                byShop.setStrRepayPort("不可归还");
                byShop.setRemark("温馨提示：无法归还充电宝，请选择其他门店");
            }
            if (byShop.getLendPort() == 0 && byShop.getRepayPort() == 0) {
                byShop.setStrLendPort("不可租用");
                byShop.setStrRepayPort("不可归还");
                byShop.setRemark("温馨提示：无法出借和归还充电宝，请选择其他门店");
            }
            if (byShop.getLendPort() != 0 && byShop.getRepayPort() != 0) {
                byShop.setStrLendPort("可租用");
                byShop.setStrRepayPort("可归还");
                byShop.setRemark("温馨提示：仓口即将还满，如要归还请尽快前往");
            }
        }
        return page.setRecords(byShops);
    }

    @Override
    public BusinessBaseVo getBusinessInfo(Integer bid) {
        BusinessBaseVo vo = new BusinessBaseVo();
        BBusiness business = businessService.validById(bid);
        BeanUtils.copyProperties(business, vo);
        vo.setCardTypeName(CardTypeEnums.getName(business.getCardType()));
        return vo;
    }

    /**
     * 查询商家中心数据
     * @param bid
     * @return
     */
    @Override
    public BusinessDetailVo getBusinessCenter(Integer bid) {
        BusinessDetailVo vo = new BusinessDetailVo();
        BBusiness business = businessService.validById(bid);
        BeanUtils.copyProperties(business, vo);
        // 拼接营业时间
        String businessDay = WeekEnums.getName(business.getStartDay()) + "~" + WeekEnums.getName(business.getEndDay());
        vo.setBusinessDay(businessDay);
        String businessTime = business.getStartTime() + "-" + business.getEndTime();
        vo.setBusinessTime(businessTime);
        List<String> coverUrlList = new ArrayList<String>();
        if (StringUtils.isNotBlank(business.getCoverUrl())) {
            JSONArray jsonArray = JSON.parseArray(business.getCoverUrl());
            for (int i = 0; i<jsonArray.size(); i++) {
                String url = "";
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
                coverUrlList.add(jsonObject.getString("url"));
            }
            vo.setCoverUrlList(coverUrlList);
        }
        return vo;
    }

    @Override
    public RestResult editBusinessCenter(Integer bid, BusinessDetailVo vo) {
        BBusiness business = businessService.validById(bid);
        if (StringUtils.isBlank(vo.getNickName())) {
            return RestResult.fail("business_name_empty");
        }
        if (vo.getStartDay() == null || vo.getEndDay() == null) {
            return RestResult.fail("business_hour_empty");
        }
        if (vo.getStartDay() >= vo.getEndDay()) {
            return RestResult.fail("business_hour_error");
        }
        if (StringUtils.isBlank(vo.getStartTime()) || StringUtils.isBlank(vo.getEndTime())) {
            return RestResult.fail("business_hour_time_empty");
        }
        if (StringUtils.isBlank(vo.getAddressDetail())) {
            return RestResult.fail("business_addr_detail_empty");
        }
        if (StringUtils.isBlank(vo.getHeadUrl())) {
            return RestResult.fail("business_headurl_empty");
        }
        if (vo.getCoverUrlList() == null || vo.getCoverUrlList().size() <= 0) {
            return RestResult.fail("business_cover_empty");
        }
        if (vo.getCoverUrlList().size()>5) {
            return RestResult.fail("business_cover_size");
        }
        // 将图片重新封装一次
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for(String coverUrl : vo.getCoverUrlList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("url", coverUrl);
            mapList.add(map);
        }
        business.setHeadUrl(vo.getHeadUrl());
        business.setNickName(vo.getNickName());
        business.setStartDay(vo.getStartDay());
        business.setEndDay(vo.getEndDay());
        business.setStartTime(vo.getStartTime());
        business.setEndTime(vo.getEndTime());
        business.setAddressDetail(vo.getAddressDetail());
        business.setCoverUrl(JSON.toJSONString(mapList));
        boolean result = businessService.updateById(business);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    @Override
    public BusinessHomeVo getBusinessHome(Integer bid) {
        BBusiness business = businessService.validById(bid);
        BusinessHomeVo vo = new BusinessHomeVo();
        vo.setHeadUrl(business.getHeadUrl());
        vo.setNickName(business.getNickName());
        boolean tips = false;
        // 查询是否有未读消息
        QueryWrapper<CPushLog> pushLogWrapper = new QueryWrapper<>();
        pushLogWrapper.eq("target_id", bid).eq("target_type", CashTypeEnums.BUSINESS.getId())
                .eq("reading", StaticUtils.STATUS_NO);
        int logNum = pushLogService.count(pushLogWrapper);
        if (logNum>0) {
            tips = true;
        }
        vo.setTips(tips);
        // 余额、收益
        BAccount bAccount = bAccountService.getByBid(bid);
        vo.setMoney(bAccount.getMoney());
        vo.setTotalIncome(bAccount.getTotalIncome());
        BigDecimal todayIncome = bMoneyDetailMapper.getIncome(bid, StaticUtils.TIME_DAY);
        BigDecimal weekIncome = bMoneyDetailMapper.getIncome(bid, StaticUtils.TIME_WEEK);
        BigDecimal monthIncome = bMoneyDetailMapper.getIncome(bid, StaticUtils.TIME_MONTH);
        vo.setTodayIncome(todayIncome);
        vo.setWeekIncome(weekIncome);
        vo.setMonthIncome(monthIncome);
        return vo;
    }

    @Override
    public void queryMoneyDetailList(SPage<BMoneyDetail> page, BMoneyDetail detail) {
        bMoneyDetailMapper.queryMoneyDetailList(page, detail);
        for (BMoneyDetail d : page.getRecords()) {
            d.setTypeName(HandleTypeEnums.getName(d.getTypeId()));
        }
    }

    @Override
    public Map<String, Object> queryIncomeStatistics(Integer bid, Integer type) {
        JSONArray dateArray = new JSONArray();
        // 营收额
        JSONArray moneyArray = new JSONArray();
        try {
            // 当前时间
            Date date = new Date();
            List<String> dateList = getDateListByType(type, date);
            QueryTimeDataVo vo = new QueryTimeDataVo();
            vo.setType(type);
            // 循环时间集
            for (int i = 0; i< dateList.size(); i++) {
                String data = dateList.get(i);
                vo.setDate(data);
                if (type == StaticUtils.TIME_WEEK) {
                    if (i==dateList.size()-1) {
                        vo.setEndDate(DateUtil.toDateString(DateUtil.getEndTimeOfMonth(date)));
                    } else {
                        vo.setEndDate(dateList.get(i+1));
                    }
                }
                BigDecimal money = bMoneyDetailMapper.queryBusinessIncome(vo);
                moneyArray.add(money);
                dateArray.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", dateArray);
        map.put("series", moneyArray);
        return map;
    }

    /**
     * 通过类型获得不同的时间集
     * @param type 1 day, 2 week, 3 month
     * @param time 时间
     * @return List 时间集
     */
    public List<String> getDateListByType (Integer type, Date time) {
        List<String> dateList = new ArrayList<>();
        if (FunctionUtils.isEquals(type, StaticUtils.TIME_DAY)) {
            dateList = DateUtil.getWeekDays(time);
        } else if (FunctionUtils.isEquals(type, StaticUtils.TIME_WEEK)) {
            dateList = DateUtil.getIntervalDay(time, 5);
        }
        return dateList;
    }
}
