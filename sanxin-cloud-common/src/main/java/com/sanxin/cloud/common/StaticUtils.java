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
    public static final Integer SATUS_YES=1;
    public static final Integer SATUS_NO=0;

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
}
