package com.sanxin.cloud.netty.service;

import com.sanxin.cloud.entity.BDeviceTerminal;
import io.netty.channel.ChannelHandlerContext;

/**
 * 机柜通信业务处理service
 * @author xiaoky
 * @date 2019-09-23
 */
public interface HandleService {

    /**
     * 处理归还充电宝
     * @param boxId 机柜编号
     * @param slot 槽位
     * @param terminalId 充电宝Id
     * @return
     */
    String handleReturnTerminal(String boxId, String slot, String terminalId, ChannelHandlerContext ctx);

    /**
     * 操作-更新充电宝数据
     * @param terminal
     * @return
     */
    Boolean handleUpdateTerminal(BDeviceTerminal terminal);

    /**
     * 操作-更新机柜可租借数量
     * @param code 设备编号
     * @param terminalNum
     * @return
     */
    Boolean handleUpdateDeviceRentNum(String code, Integer terminalNum);

    /**
     * 获取电量最多的充电宝
     * @param boxId 机柜id
     * @return
     */
    String getMostCharge(String boxId);

    /**
     * 成功借充电宝
     * @param terminalId
     */
    void handleLendSuccess(String terminalId);
}
