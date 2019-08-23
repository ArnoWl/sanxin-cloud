package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.entity.SysConfig;
import com.sanxin.cloud.mapper.SysConfigMapper;
import com.sanxin.cloud.service.ISysConfigService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author arno
 * @since 2019-08-23
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public List<SysConfig> queryTest() {
        return baseMapper.getOneTest();
    }
}
