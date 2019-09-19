package com.sanxin.cloud.app.api.service;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.DeviceDetailVo;
import com.sanxin.cloud.entity.BDevice;

/**
 * 设备管理service
 * @author xiaoky
 * @date 2019-09-17
 */
public interface DeviceService {

    /**
     * 查询柜机数量
     * @param bid
     * @param type
     * @return
     */
    int countByType(Integer bid, Integer type);

    /**
     * 查询柜机列表
     * @param page
     * @param type 类型，大柜机，小柜机
     * @param key 暂时无用，设计图上有一个搜索
     * @return
     */
    void queryDeviceList(SPage<BDevice> page, Integer type, String key);

    /**
     * 通过柜机编号查询柜机详情
     * @param deviceId
     * @param bid
     * @return
     */
    DeviceDetailVo getDeviceDetail(Integer deviceId, Integer bid);
}
