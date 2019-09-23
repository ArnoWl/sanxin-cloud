package com.sanxin.cloud.common;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-24
 */
public class StaticUtils {

    /**
     * 是否有效 1有效 0无效
     */
    public static final Integer STATUS_YES=1;
    public static final Integer STATUS_NO=0;

    /**
     * 申请状态，申请中(val=1)
     */
    public static final int STATUS_APPLY = 1;

    /**
     * 申请状态，审核通过(val=2)
     */
    public static final int STATUS_SUCCESS = 2;

    /**
     * 申请状态，审核驳回(val=3)
     */
    public static final int STATUS_FAIL = 3;

    /**
     * 申请状态，超时(val=4)
     */
    public static final int STATUS_TIME_OUT = 4;
    /**
     * 默认密码(val=888888)
     */
    public static final String DEFAULT_PWD = "888888";

    /**
     * 不限制提现次数(val=0)
     */
    public static final Integer CASH_NO_TYPE = 0;
    /**
     * 限制日提现次数(val=1)
     */
    public static final Integer CASH_DAY_TYPE = 1;
    /**
     * 限制周提现次数(val=2)
     */
    public static final Integer CASH_WEEK_TYPE = 2;
    /**
     * 限制月提现次数(val=3)
     */
    public static final Integer CASH_MOUTH_TYPE = 3;
    /**
     * 登录类型——用户(val=1)
     */
    public static final Integer LOGIN_CUSTOMER = 1;
    /**
     * 登录类型——加盟商(val=2)
     */
    public static final Integer LOGIN_BUSINESS = 2;

    /**
     * 支出收入类型 支出(val=0)
     */
    public static final Integer PAY_OUT = 0;
    /**
     * 支出收入类型 收入(val=1)
     */
    public static final Integer PAY_IN = 1;
    /**
     * 数据查询-日期
     */
    public static final Integer TIME_DAY = 1;
    /**
     * 数据查询-星期
     */
    public static final Integer TIME_WEEK = 2;
    /**
     * 数据查询-月
     */
    public static final Integer TIME_MONTH = 3;
    /**
     * 类型-登录密码(val=1)
     */
    public static final int TYPE_PASS_WORD = 1;
    /**
     * 类型-支付密码(val=2)
     */
    public static final int TYPE_PAY_WORD = 2;
}
