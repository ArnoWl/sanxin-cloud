package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface CPushLogService extends IService<CPushLog>{

    IPage<CPushLog> queryMyMessage(SPage<CPushLog> page, Integer token);
}
