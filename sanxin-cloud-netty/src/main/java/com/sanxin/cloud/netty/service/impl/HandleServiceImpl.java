package com.sanxin.cloud.netty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.dto.OrderReturnVo;
import com.sanxin.cloud.entity.BDevice;
import com.sanxin.cloud.entity.BDeviceTerminal;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.enums.DeviceTypeEnums;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.TerminalStatusEnums;
import com.sanxin.cloud.netty.properties.NettySocketHolder;
import com.sanxin.cloud.netty.service.HandleService;
import com.sanxin.cloud.service.BDeviceService;
import com.sanxin.cloud.service.BDeviceTerminalService;
import com.sanxin.cloud.service.OrderMainService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 机柜通信业务处理service实现类
 * @author xiaoky
 * @date 2019-09-23
 */
@Service
public class HandleServiceImpl implements HandleService {
    @Autowired
    private BDeviceTerminalService bDeviceTerminalService;
    @Autowired
    private BDeviceService bDeviceService;
    @Autowired
    private OrderMainService orderMainService;

    @Override
    public String handleReturnTerminal(String boxId, String slot, String terminalId, ChannelHandlerContext ctx) {
        BDeviceTerminal terminal = bDeviceTerminalService.getTerminalById(terminalId);
        // 非法充电宝 ID
        if (terminal == null) {
            return "04";
        }
        // 充电宝状态异常(当数据库充电宝不是借出状态)
        if (!FunctionUtils.isEquals(terminal.getStatus(), TerminalStatusEnums.LENT.getStatus())) {
            // 如果充电宝当前状态是充电中-重复归还
            if (FunctionUtils.isEquals(terminal.getStatus(), TerminalStatusEnums.CHARGING.getStatus())) {
                return "03";
            }
            return "02";
        }

        // 判断当前槽位是否已有其它充电宝了——需要去机柜查数据（如果有其它充电宝-返回05）
        QueryWrapper<BDeviceTerminal> wrapper = new QueryWrapper<>();
        wrapper.eq("d_code", boxId).eq("slot", slot);
        System.out.println("归还充电宝 操作-机柜编号"+boxId);
        System.out.println("归还充电宝 操作-槽位编号"+slot);
        List<BDeviceTerminal> terminalList = bDeviceTerminalService.list(wrapper);
        System.out.println("归还充电宝 操作-槽位结果集"+ terminalList);
        System.out.println("归还充电宝 操作-槽位结果集"+ terminalList.toString());
        if (terminalList != null && terminalList.size()>=1) {
            return "05";
        }
        // 无误，操作充电宝数据
        terminal.setSlot(slot);
        terminal.setStatus(TerminalStatusEnums.CHARGING.getStatus());
        terminal.setdCode(boxId);
        terminal.setLastRevertTime(new Date());
        System.out.println("归还充电宝——操作充电宝数据——开始");
        boolean result = bDeviceTerminalService.updateById(terminal);
        System.out.println("归还充电宝——操作充电宝数据——结果"+result);
        if (!result) {
            // 数据操作失败
            return "00";
        }

        // TODO 对充电宝对应的订单进行操作

        // 成功
        return "01";
    }

    @Override
    public Boolean handleUpdateTerminal(BDeviceTerminal terminal) {
        UpdateWrapper<BDeviceTerminal> wrapper = new UpdateWrapper<>();
        wrapper.eq("terminal_id", terminal.getTerminalId());
        BDeviceTerminal queryTerminal = bDeviceTerminalService.getOne(wrapper);
        if (queryTerminal == null) {
            return bDeviceTerminalService.save(terminal);
        } else {
            return bDeviceTerminalService.update(terminal, wrapper);
        }
    }

    @Override
    public Boolean handleUpdateDeviceRentNum(String code, Integer terminalNum) {
        BDevice bDevice = bDeviceService.getByCode(code);
        if (bDevice == null) {
            return false;
        }
        bDevice.setLendPort(terminalNum);
        bDevice.setRepayPort(bDevice.getAllPort()-terminalNum);
        return bDeviceService.updateById(bDevice);
    }

    @Override
    public Map<String, String> getMostCharge(String boxId) {
        QueryWrapper<BDeviceTerminal> wrapper = new QueryWrapper<>();
        wrapper.eq("d_code", boxId);
        wrapper.orderByDesc("level");
        List<BDeviceTerminal> terminalList = bDeviceTerminalService.list(wrapper);
        if (terminalList == null || terminalList.size()<=0) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("slot", terminalList.get(0).getSlot());
        map.put("terminalId", terminalList.get(0).getTerminalId());
        return map;
    }

    @Override
    public void handleLendSuccess(String terminalId) {
        BDeviceTerminal terminal = bDeviceTerminalService.getTerminalById(terminalId);
        if (terminal != null) {
            terminal.setStatus(TerminalStatusEnums.LENT.getStatus());
            terminal.setLevel(-1);
            terminal.setdCode("");
            terminal.setSlot("");
            terminal.setLendNum(terminal.getLendNum()+1);
            terminal.setLastLendTime(new Date());
            bDeviceTerminalService.updateById(terminal);
        }
    }

    @Override
    public Integer queryCidByTerminalId(String terminalId) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id", terminalId);
        wrapper.orderByDesc("create_time");
        List<OrderMain> list = orderMainService.list(wrapper);
        if (list != null && list.size()>0) {
            return list.get(0).getCid();
        }
        return null;
    }

    @Override
    public RestResult queryReturnMsg(String cid, String terminalId) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid).eq("terminal_id", terminalId);
        wrapper.orderByDesc("create_time");
        List<OrderMain> list = orderMainService.list(wrapper);
        OrderMain orderMain = null;
        if (list != null && list.size()>0) {
            orderMain = list.get(0);
        }
        OrderReturnVo vo = new OrderReturnVo();
        BeanUtils.copyProperties(orderMain, vo);
        Date endTime = new Date();
        // 使用时长
        if (!FunctionUtils.isEquals(orderMain.getOrderStatus(), OrderStatusEnums.USING.getId())) {
            // 如果订单正在使用中，计算使用时长应该用当前时间和借出时间算
            // 其它状态用归还时间算
            endTime = orderMain.getReturnTime();
        }
        String useHour = DateUtil.dateDiff(orderMain.getRentTime().getTime(), endTime.getTime());
        vo.setUseHour(useHour);
        vo.setMoney(orderMain.getPayMoney());
        return RestResult.success("success", vo);
    }

    @Override
    public Boolean handleSaveDevice(String boxId, Integer terminalNum) {
        BDevice bDevice = new BDevice();
        bDevice.setCode(boxId);
        bDevice.setAllPort(terminalNum);
        if (terminalNum > 8) {
            bDevice.setType(DeviceTypeEnums.LARGE_CABINET.getType());
        }
        UpdateWrapper<BDevice> wrapper = new UpdateWrapper<>();
        wrapper.eq("code", boxId);
        BDevice queryDevice = bDeviceService.getOne(wrapper);
        if (queryDevice == null) {
            return bDeviceService.save(bDevice);
        } else {
            return bDeviceService.update(bDevice, wrapper);
        }
    }
}
