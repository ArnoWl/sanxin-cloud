package com.sanxin.cloud.app.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.entity.BBusiness;

public interface BusinessService {
    /**
     * 根据经纬度分页查询周边商铺
     * @param current
     * @param size
     * @param latVal
     * @param longitude
     * @param radius
     * @return
     */
    IPage<BBusiness> findByShops(Integer current, Integer size, String latVal, String longitude, Integer radius);
}
