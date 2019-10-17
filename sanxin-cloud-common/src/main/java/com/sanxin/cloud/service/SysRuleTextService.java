package com.sanxin.cloud.service;

import com.sanxin.cloud.dto.RuleTextVo;
import com.sanxin.cloud.entity.SysRuleText;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统规则表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-17
 */
public interface SysRuleTextService extends IService<SysRuleText> {

    /**
     * 通过类型查询系统规则
     * @param type
     * @return
     */
    RuleTextVo getByType(Integer type);
}
