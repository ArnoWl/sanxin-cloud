package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.entity.SysCashRule;
import com.sanxin.cloud.mapper.SysCashRuleMapper;
import com.sanxin.cloud.service.SysCashRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Map<String, Object> getCashRule(Integer type) {
        Map<String, Object> map = new HashMap<>();
        SysCashRule cashRule = getRuleByType(type);
        if (cashRule != null) {
            map.put("scale", cashRule.getScale());
            map.put("minVal", cashRule.getMinVal());
            map.put("maxVal", cashRule.getMaxVal());
            map.put("multiple", cashRule.getMultiple());
            map.put("taxOne", cashRule.getTaxOne());
            map.put("taxTwo", cashRule.getTaxTwo());
        }
        return map;
    }
}
