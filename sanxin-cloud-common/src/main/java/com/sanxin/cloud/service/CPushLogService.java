package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CFeedbackLog;
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

    RestResult queryMyMessage(SPage<CPushLog> page, Integer token);
}
