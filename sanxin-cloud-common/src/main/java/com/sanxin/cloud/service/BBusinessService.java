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


    IPage<BBusiness> findByShops(Integer current, Integer size, String latVal, String longitude, Integer radius, String province, String city, String district);

}
