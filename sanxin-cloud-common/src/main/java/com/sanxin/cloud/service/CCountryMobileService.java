package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.CCountryMobile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Arno
 * @since 2019-10-28
 */
public interface CCountryMobileService extends IService<CCountryMobile> {

    RestResult getAreaCode();
}
