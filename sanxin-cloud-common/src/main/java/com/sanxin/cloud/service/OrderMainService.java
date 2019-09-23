package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.OrderMain;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单记录表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-21
 */
public interface OrderMainService extends IService<OrderMain> {

    /**
     * 通过订单编号查询订单
     * @param orderCode
     * @return
     */
    OrderMain getByOrderCode(String orderCode);
}
