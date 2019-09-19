package com.sanxin.cloud.app.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.dto.BusinessBaseVo;
import com.sanxin.cloud.dto.BusinessDetailVo;
import com.sanxin.cloud.dto.PowerBankListVo;
import com.sanxin.cloud.entity.BBusiness;

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
}
