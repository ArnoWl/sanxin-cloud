package com.sanxin.cloud.service.system.pay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.enums.*;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
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
    @Autowired
    private InfoParamService infoParamService;
    @Autowired
    private CAccountService cAccountService;
    @Autowired
    private HandleAccountChangeService handleAccountChangeService;

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
        RestResult result = handleReturnTerminal(boxId, slot, terminalId);
        if (!result.status) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 借出——更新机柜信息
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
     * 归还——更新机柜信息
     * @param device
     * @return
     */
    private boolean handleReturnDeviceMsg(BDevice device) {
        // 可归还口和可借出口数量操作
        device.setLendPort(device.getLendPort()+1);
        device.setRepayPort(device.getRepayPort()-1);
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
    private RestResult handleReturnTerminal(String boxId, String slot, String terminalId) {
        BDeviceTerminal terminal = bDeviceTerminalService.getTerminalById(terminalId);
        // 非法充电宝 ID
        if (terminal == null) {
            return RestResult.fail("fail", "04");
        }
        // 充电宝状态异常(当数据库充电宝不是借出状态)
        if (!FunctionUtils.isEquals(terminal.getStatus(), TerminalStatusEnums.LENT.getStatus())) {
            // 如果充电宝当前状态是充电中-重复归还
            if (FunctionUtils.isEquals(terminal.getStatus(), TerminalStatusEnums.CHARGING.getStatus())) {
                return RestResult.fail("fail", "03");
            }
            return RestResult.fail("fail", "02");
        }

        BDevice device = bDeviceService.getByCode(boxId);
        if (device == null) {
            return RestResult.fail("fail", "00");
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
            return RestResult.fail("fail", "05");
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
            return RestResult.fail("fail", "00");
        }
        // 归还-更新机柜信息
        result = handleReturnDeviceMsg(device);
        if (!result) {
            // 数据操作失败
            return RestResult.fail("fail", "00");
        }
        BBusiness business = businessService.validById(device.getBid());
        // 操作订单数据
        QueryWrapper<OrderMain> wrapperOrder = new QueryWrapper<>();
        wrapperOrder.eq("terminal_id", terminalId).eq("order_status", OrderStatusEnums.USING.getId());
        List<OrderMain> list = orderMainService.list(wrapperOrder);
        if (list == null || list.size()!=1) {
            return RestResult.fail("fail", "00");
        }
        OrderMain orderMain = list.get(0);
        // 计算一共使用了多少个小时
        orderMain.setReturnTime(DateUtil.currentDate());
        // 操作时长-余额
        int hour = DateUtil.dateDiffHour(orderMain.getRentTime(), orderMain.getReturnTime());
        // 查询是否有时长
        CAccount account = cAccountService.getByCid(orderMain.getCid());
        if (account == null) {
            return RestResult.fail("fail", "00");
        }

        Integer orderStatus = OrderStatusEnums.CONFIRMED.getId();
        String valueStr = infoParamService.getValueByCode(ParamCodeEnums.USE_HOUR_MONEY.getCode());
        // 一小时多少钱
        BigDecimal value = FunctionUtils.getValueByClass(BigDecimal.class, valueStr);
        // 租金总额
        BigDecimal rentMoney = FunctionUtils.div(value, new BigDecimal(hour), 2);
        // 实际扣除时长
        Integer realHour = hour;
        // 实际扣除余额
        BigDecimal payMoney = BigDecimal.ZERO;
        // 有时长，先扣时长
        if (account.getHour() > 0) {
            // 计算能扣多少时长-如果有这么多时长直接扣，时长不足就有多少扣多少
            if (account.getHour()<hour) {
                // 时长不足
                realHour = account.getHour();
                // 计算应该扣多少余额
                payMoney = new BigDecimal(hour - realHour);
                payMoney = FunctionUtils.div(value, payMoney, 2);
            } else {
                orderMain.setPayType(PayTypeEnums.MONEY.getId());
                orderMain.setOverTime(DateUtil.currentDate());
                orderStatus = OrderStatusEnums.OVER.getId();
            }
        }

        String msg = "";
        // 统一扣除时长
        if (realHour > 0) {
            msg = handleAccountChangeService.insertCHourDetail(new CHourDetail(orderMain.getCid(), HandleTypeEnums.ORDER.getId(),
                    StaticUtils.PAY_OUT, orderMain.getPayCode(), realHour, HandleTypeEnums.getName(HandleTypeEnums.ORDER.getId())));
        }
        if (StringUtils.isNotEmpty(msg)) {
            return RestResult.fail("fail", "00");
        }

        // 判断是否需要余额支付和是否开启免密支付
        if (payMoney.compareTo(BigDecimal.ZERO) > 0
                && FunctionUtils.isEquals(StaticUtils.STATUS_SUCCESS, account.getFreeSecret())) {
            msg = handleAccountChangeService.insertCMoneyDetail(new CMoneyDetail(orderMain.getCid(), HandleTypeEnums.ORDER.getId(),
                    StaticUtils.PAY_OUT, orderMain.getPayCode(), payMoney, HandleTypeEnums.getName(HandleTypeEnums.ORDER.getId())));
            if (StringUtils.isEmpty(msg)) {
                orderMain.setPayType(PayTypeEnums.MONEY.getId());
                orderMain.setOverTime(DateUtil.currentDate());
                orderStatus = OrderStatusEnums.OVER.getId();
            } else {
                // 余额不足或其它情况
                return RestResult.success("success", "01", 1);
            }
        }
        // 操作赋值
        BigDecimal realMoney = payMoney;
        orderMain.setRentMoney(rentMoney);
        orderMain.setRealMoney(realMoney);
        orderMain.setPayMoney(payMoney);
        orderMain.setHour(realHour);
        orderMain.setOrderStatus(orderStatus);
        orderMain.setReturnAddr(business.getAddressDetail());
        result = orderMainService.updateById(orderMain);
        if (!result) {
            // 数据操作失败
            return RestResult.fail("fail", "00");
        }
        // 成功
        return RestResult.success("success", "01");
    }

}
