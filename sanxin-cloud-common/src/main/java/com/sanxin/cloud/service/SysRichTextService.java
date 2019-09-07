package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysRichText;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.enums.RichTextEnums;

/**
 * <p>
 * 单个富文本 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-02
 */
public interface SysRichTextService extends IService<SysRichText> {

    /**
     * 通过类型查询数据
     * @param type 类型
     * @return
     */
    SysRichText getByType(Integer type);

    /**
     * 通过type修改信息
     * @param richText 内容
     * @return
     */
    RestResult updateRichTextByType(SysRichText richText);
}
