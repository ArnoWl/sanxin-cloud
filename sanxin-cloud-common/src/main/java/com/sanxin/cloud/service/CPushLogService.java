package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CPushLog;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-09-17
 */
public interface CPushLogService extends IService<CPushLog> {

    IPage<CPushLog> queryMyMessage(SPage<CPushLog> page, Integer token);

    /**
     * 已读消息
     * @param id 消息id
     * @param type 已读类型
     * @param cid 用户di
     * @return
     */
    RestResult readMessage(Integer id, Integer type, Integer cid);
}
