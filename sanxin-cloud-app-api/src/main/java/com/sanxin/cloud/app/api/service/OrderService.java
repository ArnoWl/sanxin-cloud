package com.sanxin.cloud.app.api.service;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.OrderBusDetailVo;
import com.sanxin.cloud.dto.OrderBusVo;
import com.sanxin.cloud.dto.OrderUserDetailVo;
import com.sanxin.cloud.dto.OrderUserVo;
import com.sanxin.cloud.entity.OrderMain;

/**
 * 订单Service
 * @author xiaoky
 * @date 2019-09-21
 */
public interface OrderService {

    /**
     * 查询加盟商订单列表(加盟商)
     * @param page
     * @param orderMain
     */
    SPage<OrderBusVo> queryBusinessOrderList(SPage<OrderMain> page, OrderMain orderMain);

    /**
     * 查询加盟商订单详情(加盟商)
     * @param bid
     * @param orderCode
     * @return
     */
    OrderBusDetailVo getBusinessOrderDetail(Integer bid, String orderCode);

    /**
     * 查询用户订单列表
     * @param page
     * @param orderMain
     */
    SPage<OrderUserVo> queryUserOrderList(SPage<OrderMain> page, OrderMain orderMain);

    /**
     * 查询用户订单详情
     * @param cid
     * @param orderCode
     * @return
     */
    OrderUserDetailVo getUserOrderDetail(Integer cid, String orderCode);
}
