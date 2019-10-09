package com.sanxin.cloud.app.api.common;

/**
 * 订单地址映射
 * @author xiaoky
 * @date 2019-09-21
 */
public class OrderMapping {
    /** 借充电宝*/
    public static final String GET_BORROW_POWER_BANK = "/getBorrowPowerBank";
    /** 查询加盟商订单列表*/
    public static final String QUERY_BUSINESS_ORDER_LIST = "/queryBusinessOrderList";
    /** 查询加盟商订单详情*/
    public static final String GET_BUSINESS_ORDER_DETAIL = "/getBusinessOrderDetail";
    /** 查询用户订单列表*/
    public static final String QUERY_USER_ORDER_LIST = "/queryUserOrderList";
    /** 查询用户订单详情*/
    public static final String GET_USER_ORDER_DETAIL = "/getUserOrderDetail";
    /** 查询是否有未支付订单*/
    public static final String QUERY_NO_COMPLETE_ORDER = "/queryNoCompleteOrder";
    /** 借充电宝扫码时判断是否交了押金*/
    public static final String VALID_RECHARGE_DEPOSIT = "/validRechargeDeposit";
}
