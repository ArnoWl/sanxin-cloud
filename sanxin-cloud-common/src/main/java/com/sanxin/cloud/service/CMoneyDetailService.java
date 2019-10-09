package com.sanxin.cloud.service;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户商余额明细 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-09-20
 */
public interface CMoneyDetailService extends IService<CMoneyDetail> {

    /**
     * 用户金额明细列表
     * @param page
     * @param cMoneyDetail
     */
    void queryCustomerAmountDetails(SPage<CMoneyDetail> page, CMoneyDetail cMoneyDetail);
}
