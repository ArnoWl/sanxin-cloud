package com.sanxin.cloud.app.api.common;

/**
 * 订单地址映射
 * @author xiaoky
 * @date 2019-09-21
 */
public class OrderMapping {
    /** 查询订单列表(加盟商)*/
    public static final String QUERY_BUSINESS_ORDER_LIST = "/queryBusinessOrderList";
    /** 查询订单详情(加盟商)*/
    public static final String GET_BUSINESS_ORDER_DETAIL = "/getBusinessOrderDetail";
    /** 查询订单列表(用户)*/
    public static final String QUERY_USER_ORDER_LIST = "/queryUserOrderList";
    /** 查询订单详情(用户)*/
    public static final String GET_USER_ORDER_DETAIL = "/getUserOrderDetail";
}
