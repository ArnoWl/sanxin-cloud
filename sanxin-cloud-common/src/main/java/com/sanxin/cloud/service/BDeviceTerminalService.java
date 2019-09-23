package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.BDeviceTerminal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 充电宝信息表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-17
 */
public interface BDeviceTerminalService extends IService<BDeviceTerminal> {

    /**
     * 通过充电宝id查询充电宝
     * @param terminalId
     * @return
     */
    BDeviceTerminal getTerminalById(String terminalId);
}
