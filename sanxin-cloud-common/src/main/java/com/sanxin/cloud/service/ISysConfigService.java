package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.entity.SysConfig;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author arno
 * @since 2019-08-23
 */
public interface ISysConfigService extends IService<SysConfig> {

    List<SysConfig> queryTest();
}
