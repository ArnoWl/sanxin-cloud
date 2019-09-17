package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.entity.AgAgent;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.mapper.AgAgentMapper;
import com.sanxin.cloud.service.AgAgentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代理 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-29
 */
@Service
public class AgAgentServiceImpl extends ServiceImpl<AgAgentMapper, AgAgent> implements AgAgentService {

    @Override
    public AgAgent getByCid(Integer cid) {
        QueryWrapper<AgAgent> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid);
        return super.getOne(wrapper);
    }
}
