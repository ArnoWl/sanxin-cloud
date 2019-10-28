package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 地址基础表 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-10-28
 */
public interface AddressService extends IService<Address> {

    RestResult getAddress(Integer parent);
}
