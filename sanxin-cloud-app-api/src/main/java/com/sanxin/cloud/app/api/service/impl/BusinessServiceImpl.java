package com.sanxin.cloud.app.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.dto.BusinessBaseVo;
import com.sanxin.cloud.dto.BusinessDetailVo;
import com.sanxin.cloud.dto.PowerBankListVo;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.enums.CardTypeEnums;
import com.sanxin.cloud.enums.WeekEnums;
import com.sanxin.cloud.mapper.BBusinessMapper;
import com.sanxin.cloud.service.BBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BBusinessMapper, BBusiness> implements BusinessService {
    @Autowired
    private BBusinessService businessService;

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
}
