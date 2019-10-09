package com.sanxin.cloud.service;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CTimeDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户时长明细表 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-09-27
 */
public interface CTimeDetailService extends IService<CTimeDetail> {

    /**
     * 时长明细列表
     * @param page
     * @param cTimeDetail
     */
    void queryCustomeTimeDetails(SPage<CTimeDetail> page, CTimeDetail cTimeDetail);
}