package com.sanxin.cloud.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.dto.RuleTextVo;
import com.sanxin.cloud.entity.SysRuleText;
import com.sanxin.cloud.mapper.SysRuleTextMapper;
import com.sanxin.cloud.service.SysRuleTextService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统规则表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-17
 */
@Service
public class SysRuleTextServiceImpl extends ServiceImpl<SysRuleTextMapper, SysRuleText> implements SysRuleTextService {

    @Override
    public RuleTextVo getByType(Integer type) {
        RuleTextVo vo = new RuleTextVo();
        QueryWrapper<SysRuleText> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        List<SysRuleText> list = super.list(wrapper);
        if (list != null && list.size()>0) {
            SysRuleText ruleText = list.get(0);
            String language = LanguageUtils.getLanguage();
            JSONObject titleObj = JSONObject.parseObject(ruleText.getTitle());
            vo.setTitle(titleObj.getString(language));
            JSONObject contentObj = JSONObject.parseObject(ruleText.getContent());
            JSONArray contentArr = contentObj.getJSONArray(language);
            vo.setContent(contentArr);
        }
        return vo;
    }
}
