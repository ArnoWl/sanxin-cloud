package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysRichText;
import com.sanxin.cloud.mapper.SysRichTextMapper;
import com.sanxin.cloud.service.SysRichTextService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 单个富文本 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-02
 */
@Service
public class SysRichTextServiceImpl extends ServiceImpl<SysRichTextMapper, SysRichText> implements SysRichTextService {

    @Override
    public SysRichText getByType(Integer type) {
        QueryWrapper<SysRichText> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        SysRichText richText = super.getOne(wrapper);
        return richText;
    }

    @Override
    public RestResult updateRichTextByType(Integer type, String content) {
        if (StringUtils.isBlank(content)) {
            return RestResult.fail("The content cannot empty");
        }
        SysRichText richText = new SysRichText();
        richText.setContent(content);
        UpdateWrapper<SysRichText> wrapper = new UpdateWrapper<>();
        wrapper.eq("type", type);
        boolean result = super.update(richText, wrapper);
        if (!result) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }
}
