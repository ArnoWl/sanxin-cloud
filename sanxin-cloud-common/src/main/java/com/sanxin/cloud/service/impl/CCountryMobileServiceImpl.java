package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.CCountryMobile;
import com.sanxin.cloud.mapper.CCountryMobileMapper;
import com.sanxin.cloud.service.CCountryMobileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-10-28
 */
@Service
public class CCountryMobileServiceImpl extends ServiceImpl<CCountryMobileMapper, CCountryMobile> implements CCountryMobileService {
    @Autowired
    private CCountryMobileMapper countryMobileMapper;


    @Override
    public RestResult getAreaCode() {
        List<CCountryMobile> mobiles = countryMobileMapper.selectList(null);
        List<CCountryMobile> popular = countryMobileMapper.selectList(new QueryWrapper<CCountryMobile>().eq("popular", 1));
        Map<String,List> map=new HashMap<>();
        map.put("popular",mobiles);
        map.put("ordinary",popular);
        return RestResult.success(map);
    }
}
