package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 广告内容表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-04
 */
public interface AAdvertContentService extends IService<AAdvertContent> {

    /**
     * 查询广告列表
     * @param page 分页
     * @param wrapper 查询条件
     */
    void queryAdvertContentList(SPage<AAdvertContent> page, QueryWrapper<AAdvertContent> wrapper);

    /**
     * 通过id查询数据
     * @param id
     * @return
     */
    AAdvertContent getMsgById(Integer id);

    /**
     * 设置广告为首页弹窗
     * @param id
     * @return
     */
    RestResult updateHomeShow(Integer id);
}
