package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CFeedbackLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 故障反馈 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-11
 */
public interface CFeedbackLogService extends IService<CFeedbackLog> {

    IPage<CFeedbackLog> queryFaultFeedback(SPage<CFeedbackLog> page, Integer bid);
}
