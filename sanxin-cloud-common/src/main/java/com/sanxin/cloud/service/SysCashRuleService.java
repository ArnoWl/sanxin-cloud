package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.SysCashRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public interface SysCashRuleService extends IService<SysCashRule> {

    /**
     * 通过类型查找提现规则
     * @param type 类型
     * @return
     */
    SysCashRule getRuleByType(Integer type);

    /**
     * 获取提现规则
     * @param type
     * @return
     */
    Map<String, Object> getCashRule(Integer type, Integer tid);
}
