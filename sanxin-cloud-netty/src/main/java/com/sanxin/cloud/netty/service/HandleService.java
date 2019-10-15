package com.sanxin.cloud.netty.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BDeviceTerminal;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

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
    Map<String, String> getMostCharge(String boxId);

    /**
     * 成功借充电宝
     * @param terminalId
     */
    void handleLendSuccess(String terminalId);

    /**
     * 通过充电宝编号查询正在使用的用户id
     * @param terminalId
     * @return
     */
    Integer queryCidByTerminalId(String terminalId);

    /**
     * 归还成功查询订单支付信息
     * @param cid
     * @param terminalId
     * @return
     */
    RestResult queryReturnMsg(String cid, String terminalId);

    /**
     * 修改设备总库存
     * @param boxId
     * @param terminalNum
     * @return
     */
    Boolean handleSaveDevice(String boxId, Integer terminalNum);
}
