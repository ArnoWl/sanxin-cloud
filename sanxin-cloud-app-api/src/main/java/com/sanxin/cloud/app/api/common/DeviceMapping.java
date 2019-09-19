package com.sanxin.cloud.app.api.common;

/**
 * 设备管理地址映射
 * @author xiaoky
 * @date 2019-09-17
 */
public class DeviceMapping {
    /** 设备数量*/
    public static final String DEVICE_NUM="/deviceNum";
    /** 柜机列表*/
    public static final String QUERY_DEVICE_LIST="/queryDeviceList";
    /** 柜机详情*/
    public static final String GET_DEVICE_DETAIL = "/getDeviceDetail";
    /** 操作设备状态*/
    public static final String HANDLE_DEVICE_STATUS = "/handleDeviceStatus";
    /** 编辑设备信息*/
    public static final String EDIT_DEVICE = "/editDevice";
    /** 添加设备*/
    public static final String ADD_DEVICE = "/addDevice";
    /** 删除设备*/
    public static final String DELETE_DEVICE = "/deleteDevice";
}
