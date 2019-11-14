package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.AppStartup;
import com.sanxin.cloud.mapper.AppStartupMapper;
import com.sanxin.cloud.service.AppStartupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * app启动图 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-11-14
 */
@Service
public class AppStartupServiceImpl extends ServiceImpl<AppStartupMapper, AppStartup> implements AppStartupService {

    @Autowired
    private AppStartupMapper appStartupMapper;

    @Override
    public RestResult getStartupDiagram() {
        List<AppStartup> appStartups = appStartupMapper.selectList(null);
        return RestResult.success(appStartups);
    }
}
