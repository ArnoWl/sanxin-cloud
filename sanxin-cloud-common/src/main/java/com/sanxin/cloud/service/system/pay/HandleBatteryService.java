package com.sanxin.cloud.service.system.pay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.TerminalStatusEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 充电宝借还处理Service
 * @author xiaoky
 * @date 2019-09-29
 */
@Service
public class HandleBatteryService {
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private BDeviceService bDeviceService;
    @Autowired
    private BDeviceTerminalService bDeviceTerminalService;
    @Autowired
    private BBusinessService businessService;
    @Autowired
    private OrderMainService orderMainService;

    /**
     * 借充电宝逻辑处理
     * app、小程序端扫码，调用后台接口，后台首先应该查询机柜库存，得到电量最多的充电宝
     * 再发送借充电宝指令，等待机柜响应，借用成功调用该方法处理程序流程——诸如订单之类
     * @param cid 当前借用充电宝的用户
     * @param boxId 机柜id
     * @param terminalId 充电宝id
     * @param slot 槽位
     * @param fromChannel 来源渠道
     * @return
     */
    public RestResult handleLendBattery(Integer cid, String boxId, String terminalId, String slot, Integer fromChannel) {
        // 校验用户
        CCustomer customer = customerService.getById(cid);
        if (customer == null) {
            throw new ThrowJsonException("register_user_empty");
        }
        //判断账号是否被冻结
        if (customer.getStatus() == StaticUtils.STATUS_NO) {
            throw new ThrowJsonException("register_user_freeze");
        }
        // 查询机柜信息
        BDevice device = bDeviceService.getByCode(boxId);
        if (device == null) {
            throw new ThrowJsonException("data_exception");
        }
        // 查询店铺信息
        BBusiness business = businessService.validById(device.getBid());
        // 查询充电宝信息
        BDeviceTerminal terminal = bDeviceTerminalService.getTerminalById(terminalId);
        // 校验-充电宝不存在或者充电宝状态不是充电中
        if (terminal == null || !FunctionUtils.isEquals(terminal.getStatus(), TerminalStatusEnums.CHARGING.getStatus())) {
            throw new ThrowJsonException("data_exception");
        }
        // 借出-更新充电宝信息
        boolean result = handleLendTerminalMsg(terminal);
        if (!result) {
            throw new ThrowJsonException(LanguageUtils.getMessage("fail"));
        }
        // 借出-更新机柜信息
        result = handleLendDeviceMsg(device);
        if (!result) {
            throw new ThrowJsonException(LanguageUtils.getMessage("fail"));
        }
        // 机柜-充电宝信息处理完
        // 校验订单信息-充电宝是否在被租用
        // 当前用户是否存在未支付订单
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id", terminalId).in("order_status", OrderStatusEnums.USING.getId(), OrderStatusEnums.CONFIRMED.getId());
        int orderNum = orderMainService.count(wrapper);
        if (orderNum>0) {
            throw new ThrowJsonException("充电宝被使用中");
        }
        wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid).in("order_status", OrderStatusEnums.USING.getId(), OrderStatusEnums.CONFIRMED.getId());
        orderNum = orderMainService.count(wrapper);
        if (orderNum>0) {
            throw new ThrowJsonException("您还有未完成订单");
        }
        // 生成借充电宝订单信息
        String orderCode = FunctionUtils.getOrderCode("O");
        String payCode = FunctionUtils.getOrderCode("P");
        OrderMain orderMain = new OrderMain();
        orderMain.setCid(cid);
        orderMain.setBid(business.getId());
        orderMain.setOrderCode(orderCode);
        orderMain.setPayCode(payCode);
        orderMain.setTerminalId(terminalId);
        orderMain.setCreateTime(DateUtil.currentDate());
        // TODO 确认去支付时间
        // orderMain.setConfirmTime();
        orderMain.setRentTime(DateUtil.currentDate());
        orderMain.setFromChannel(fromChannel);
        orderMain.setRentAddr(business.getAddressDetail());
        result = orderMainService.save(orderMain);
        if (!result) {
            throw new ThrowJsonException(LanguageUtils.getMessage("fail"));
        }
        return RestResult.success("success");
    }

    /**
     * 处理充电宝归还逻辑
     * @param boxId 机柜id
     * @param slot 槽位
     * @param terminalId 充电宝编号
     * @return
     */
    public RestResult handleReturnBattery(String boxId, String slot, String terminalId) {
        String code = "";
        try {
            code = handleReturnTerminal(boxId, slot, terminalId);
        } catch (Exception e) {
            code = "00";
            throw new ThrowJsonException(e.getMessage());
        }
        // 处理不成功
        if (!"01".equals(code)) {
            throw new ThrowJsonException(code);
        }
        return RestResult.success("success", code);
    }

    /**
     * 更新机柜信息
     * @param device
     * @return
     */
    private boolean handleLendDeviceMsg(BDevice device) {
        // 可归还口和可借出口数量操作
        device.setLendPort(device.getLendPort()-1);
        device.setRepayPort(device.getRepayPort()+1);
        boolean result = bDeviceService.updateById(device);
        return result;
    }

    /**
     * 借出-更新充电宝信息
     * @param terminal
     * @return
     */
    private boolean handleLendTerminalMsg(BDeviceTerminal terminal) {
        terminal.setStatus(TerminalStatusEnums.LENT.getStatus());
        terminal.setLevel(-1);
        terminal.setdCode("");
        terminal.setSlot("");
        terminal.setLendNum(terminal.getLendNum()+1);
        terminal.setLastLendTime(new Date());
        boolean result = bDeviceTerminalService.updateById(terminal);
        return result;
    }

    /**
     * 处理归还充电宝
     * @param boxId 机柜编号
     * @param slot 槽位
     * @param terminalId 充电宝Id
     * @return
     * @return
     */
    private String handleReturnTerminal(String boxId, String slot, String terminalId) {
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

        BDevice device = bDeviceService.getByCode(boxId);
        if (device == null) {
            return "00";
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
        BBusiness business = businessService.validById(device.getBid());
        // 操作订单数据
        QueryWrapper<OrderMain> wrapperOrder = new QueryWrapper<>();
        wrapperOrder.eq("terminal_id", terminalId).eq("order_status", OrderStatusEnums.USING.getId());
        List<OrderMain> list = orderMainService.list(wrapperOrder);
        if (list == null || list.size()!=1) {
            throw new ThrowJsonException("数据异常");
        }
        OrderMain orderMain = list.get(0);
        // TODO 时长-金额等处理

        orderMain.setOrderStatus(OrderStatusEnums.CONFIRMED.getId());
        orderMain.setReturnTime(DateUtil.currentDate());
        orderMain.setReturnAddr(business.getAddressDetail());
        result = orderMainService.updateById(orderMain);
        if (!result) {
            // 数据操作失败
            return "00";
        }
        // 成功
        return "01";
    }
}
