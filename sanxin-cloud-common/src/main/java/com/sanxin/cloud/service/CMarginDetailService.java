package com.sanxin.cloud.service;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CMarginDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 押金明细 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-09-19
 */
public interface CMarginDetailService extends IService<CMarginDetail> {

    /**
     * 用户押金明细列表
     * @param page
     * @param cMarginDetail
     */
    void queryCustomerDepositDetails(SPage<CMarginDetail> page, CMarginDetail cMarginDetail);
}
