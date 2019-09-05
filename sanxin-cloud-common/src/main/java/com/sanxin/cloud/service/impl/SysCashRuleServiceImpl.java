package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.entity.SysCashRule;
import com.sanxin.cloud.mapper.SysCashRuleMapper;
import com.sanxin.cloud.service.SysCashRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
@Service
public class SysCashRuleServiceImpl extends ServiceImpl<SysCashRuleMapper, SysCashRule> implements SysCashRuleService {

    @Override
    public SysCashRule getRuleByType(Integer type) {
        QueryWrapper<SysCashRule> wrapper = new QueryWrapper<>();
        wrapper.eq("role_type", type);
        return super.getOne(wrapper);
    }
}
