package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.AppStartup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * app启动图 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-11-14
 */
public interface AppStartupService extends IService<AppStartup> {

    /**
     * 获取启动图
     * @return
     */
    RestResult getStartupDiagram();
}
