package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BDevice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 店铺设备 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-06
 */
public interface BDeviceService extends IService<BDevice> {

    SPage<BDevice> queryDeviceList(SPage<BDevice> page, BDevice device);

    BDevice getDeviceDetail(Integer id);

    /**
     * 通过柜机编号查询柜机
     * @return
     */
    BDevice getByCode(String code);

    /**
     * 处理设备状态
     * @param deviceId
     * @param status
     * @return
     */
    RestResult handleDeviceStatus(Integer deviceId, Integer status);
}
