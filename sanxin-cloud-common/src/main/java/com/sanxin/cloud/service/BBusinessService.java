package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.entity.BBusiness;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
public interface BBusinessService extends IService<BBusiness> {

    BBusiness selectById(Integer id);

    /**
     * 通过用户id查询数据
     * @param cid
     * @return
     */
    BBusiness getByCid(Integer cid);

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
