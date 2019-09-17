package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.mapper.BBusinessMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BBusinessMapper, BBusiness> implements BusinessService {

    /**
     * 根据经纬度分页查询周边商铺
     * @param current
     * @param size
     * @param latVal
     * @param longitude
     * @param radius
     * @return
     */
    @Override
    public IPage<BBusiness> findByShops(Integer current, Integer size, String latVal, String longitude, Integer radius) {
        IPage<BBusiness> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        List<BBusiness> byShops = baseMapper.findByShops(page, latVal, longitude, radius);
        return page.setRecords(byShops);
    }
}
