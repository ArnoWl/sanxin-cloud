package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.app.api.service.OrderService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.OrderBusDetailVo;
import com.sanxin.cloud.dto.OrderBusVo;
import com.sanxin.cloud.dto.OrderUserDetailVo;
import com.sanxin.cloud.dto.OrderUserVo;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.enums.*;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.*;
import com.sanxin.cloud.service.*;
import com.sanxin.cloud.service.system.pay.HandleAccountChangeService;
import com.sanxin.cloud.service.system.pay.PayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单Service实现类
 *
 * @author xiaoky
 * @date 2019-09-21
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMainService orderMainService;
    @Autowired
    private OrderMainMapper orderMainMapper;
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private CCustomerMapper customerMapper;
    @Autowired
    private BBusinessService bBusinessService;
    @Autowired
    private BDeviceTerminalService bDeviceTerminalService;
    @Autowired
    private BDeviceService bDeviceService;
    @Autowired
    private CAccountMapper accountMapper;
    @Autowired
    private HandleAccountChangeService handleAccountChangeService;
    @Autowired
    private InfoParamService infoParamService;
    @Autowired
    private PayService payService;
    @Autowired
    private CPayLogService cPayLogService;
    @Autowired
    private CAccountService cAccountService;


    /**
     * 借充电宝逻辑
     * @param cid
     * @param terminalId
     */
    @Override
    public void getBorrowPowerBank(Integer cid, String terminalId) {
        //用户信息
        CCustomer customer = customerMapper.selectById(cid);
        if (customer == null) {
            return;
        }
        //用户账户
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        if (account.getDeposit().compareTo(new BigDecimal(99)) != -1) {
            return;
        }
        //充电宝
        BDeviceTerminal terminal = bDeviceTerminalService.getOne(new QueryWrapper<BDeviceTerminal>().eq("terminal_id", terminalId));
        //机柜
        BDevice code = bDeviceService.getOne(new QueryWrapper<BDevice>().eq("code", terminal.getdCode()));

        if (terminal == null) {
            return;
        }
        OrderMain orderMain=new OrderMain();
        orderMain.setCid(cid);
        orderMain.setBid(code.getBid());



    }

    @Override
    public SPage<OrderBusVo> queryBusinessOrderList(SPage<OrderMain> page, OrderMain orderMain) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("bid", orderMain.getBid()).eq("del", StaticUtils.STATUS_NO)
                .like(StringUtils.isNotBlank(orderMain.getKey()), "order_code", orderMain.getKey());
        if (orderMain.getOrderStatus() != null) {
            wrapper.eq("order_status", orderMain.getOrderStatus());
        }
        wrapper.orderByDesc("create_time");
        orderMainMapper.selectPage(page, wrapper);
        List<OrderBusVo> list = new ArrayList<>();
        for (OrderMain o : page.getRecords()) {
            OrderBusVo vo = new OrderBusVo();
            BeanUtils.copyProperties(o, vo);
            // 订单状态
            vo.setStatusName(OrderStatusEnums.getName(vo.getOrderStatus()));
            // 租借人
            CCustomer customer = customerService.getById(o.getCid());
            if (customer != null) {
                vo.setCusName(customer.getNickName());
            }

            Date endTime = new Date();
            // 使用时长
            if (!FunctionUtils.isEquals(o.getOrderStatus(), OrderStatusEnums.USING.getId())) {
                // 如果订单正在使用中，计算使用时长应该用当前时间和借出时间算
                // 其它状态用归还时间算
                endTime = o.getReturnTime();
            } else {
                //预计租金
                double time = Math.ceil(DateUtil.getInstance().calLastedTime(new Date(), o.getCreateTime()) / (60 * 60) + 1);
                vo.setRealMoney(new BigDecimal(time));
            }
            String useHour = DateUtil.dateDiff(o.getRentTime().getTime(), endTime.getTime());
            vo.setUseHour(useHour);
            list.add(vo);
        }
        SPage<OrderBusVo> pageInfo = new SPage<>();
        BeanUtils.copyProperties(page, pageInfo);
        pageInfo.setRecords(list);
        return pageInfo;
    }

    @Override
    public OrderBusDetailVo getBusinessOrderDetail(Integer bid, String orderCode) {
        OrderMain order = orderMainService.getByOrderCode(orderCode);
        if (order == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("order_not_exist"));
        }
        // 校验数据
        if (!FunctionUtils.isEquals(order.getBid(), bid)) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        BBusiness business = bBusinessService.validById(bid);
        OrderBusDetailVo vo = new OrderBusDetailVo();
        BeanUtils.copyProperties(order, vo);
        // 订单状态
        vo.setStatusName(OrderStatusEnums.getName(vo.getOrderStatus()));
        // 租借人
        CCustomer customer = customerService.getById(order.getCid());
        if (customer != null) {
            vo.setCusName(customer.getNickName());
        }
        Date endTime = new Date();
        // 使用时长
        if (!FunctionUtils.isEquals(order.getOrderStatus(), OrderStatusEnums.USING.getId())) {
            // 如果订单正在使用中，计算使用时长应该用当前时间和借出时间算
            // 其它状态用归还时间算
            endTime = order.getReturnTime();
        } else {
            //预计租金
            double time = Math.ceil(DateUtil.getInstance().calLastedTime(new Date(), order.getCreateTime()) / (60 * 60) + 1);
            vo.setRealMoney(new BigDecimal(time));
        }
        String useHour = DateUtil.dateDiff(order.getRentTime().getTime(), endTime.getTime());
        vo.setUseHour(useHour);
        // 支付方式
        vo.setPayTypeName(PayTypeEnums.getName(order.getPayType()));
        vo.setMoney(order.getPayMoney());
        vo.setReturnTime(order.getPayTime());
        vo.setAddressDetail(business.getAddressDetail());
        return vo;
    }

    /**
     * 查询用户订单列表
     *
     * @param page
     * @param orderMain
     * @return
     */
    @Override
    public SPage<OrderUserVo> queryUserOrderList(SPage<OrderMain> page, OrderMain orderMain) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", orderMain.getCid()).eq("del", StaticUtils.STATUS_NO)
                .eq("order_status", orderMain.getOrderStatus());
        IPage<OrderMain> userPage = orderMainMapper.selectPage(page, wrapper);
        List<OrderUserVo> list = new ArrayList<>();
        for (OrderMain o : userPage.getRecords()) {
            OrderUserVo vo = new OrderUserVo();
            BeanUtils.copyProperties(o, vo);
            // 订单状态
            vo.setStatusName(OrderStatusEnums.getName(vo.getOrderStatus()));
            // 订单编号
            vo.setOrderCode(o.getOrderCode());
            Date endTime = new Date();
            // 使用时长
            if (!FunctionUtils.isEquals(o.getOrderStatus(), OrderStatusEnums.USING.getId())) {
                // 如果订单正在使用中，计算使用时长应该用当前时间和借出时间算
                // 其它状态用归还时间算
                endTime = o.getReturnTime();
            } else {
                //预计租金
                double time = Math.ceil(DateUtil.getInstance().calLastedTime(new Date(), o.getCreateTime()) / (60 * 60) + 1);
                vo.setRealMoney(new BigDecimal(time));
            }
            String useHour = DateUtil.dateDiff(o.getRentTime().getTime(), endTime.getTime());
            vo.setUseHour(useHour);
            // 租借时间
            vo.setRentTime(o.getCreateTime());
            list.add(vo);
        }
        SPage<OrderUserVo> pageInfo = new SPage<>();
        BeanUtils.copyProperties(page, pageInfo);
        pageInfo.setRecords(list);
        return pageInfo;
    }

    /**
     * 查询用户订单详情
     *
     * @param cid
     * @param orderCode
     * @return
     */
    @Override
    public OrderUserDetailVo getUserOrderDetail(Integer cid, String orderCode) {
        OrderMain order = orderMainService.getByOrderCode(orderCode);
        if (order == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("order_not_exist"));
        }
        // 校验数据
        if (!FunctionUtils.isEquals(order.getCid(), cid)) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        OrderUserDetailVo vo = new OrderUserDetailVo();
        BeanUtils.copyProperties(order, vo);
        // 订单状态
        vo.setStatusName(OrderStatusEnums.getName(vo.getOrderStatus()));

        Date endTime = new Date();
        // 使用时长
        if (!FunctionUtils.isEquals(order.getOrderStatus(), OrderStatusEnums.USING.getId())) {
            // 如果订单正在使用中，计算使用时长应该用当前时间和借出时间算
            // 其它状态用归还时间算
            endTime = order.getReturnTime();
        } else {
            //预计租金
            double time = Math.ceil(DateUtil.getInstance().calLastedTime(new Date(), order.getCreateTime()) / (60 * 60) + 1);
            vo.setRealMoney(new BigDecimal(time));
        }
        String useHour = DateUtil.dateDiff(order.getRentTime().getTime(), endTime.getTime());
        vo.setUseHour(useHour);
        // 支付方式
        vo.setPayTypeName(PayTypeEnums.getName(order.getPayType()));
        vo.setMoney(order.getPayMoney());
        vo.setReturnTime(order.getPayTime());
        vo.setAddressDetail(order.getReturnAddr());
        vo.setPayTime(order.getPayTime());
        vo.setHour(order.getHour());
        return vo;
    }

    /**
     * 购买充电宝
     *
     * @param cid
     * @param payType
     * @param payWord
     * @return
     */
    @Override
    public RestResult handlePayBuyPower(Integer cid, String payCode, Integer payType, String payWord, Integer payChannel) {
        CCustomer customer = customerService.getById(cid);
        if (customer == null) {
            return RestResult.fail("register_user_empty");
        }
        // 判断参数值
        if (payType == null) {
            return RestResult.fail("pay_type_empty");
        }
        if (payChannel == null) {
            return RestResult.fail("pay_channel_empty");
        }
        // 余额支付判断支付密码
        if (FunctionUtils.isEquals(payType, PayTypeEnums.MONEY.getId())) {
            // 判断是否余额支付——余额支付需要校验密码
            if (StringUtils.isBlank(payWord)) {
                return RestResult.fail("pay_word_empty");
            }
            String encryPayword = PwdEncode.encodePwd(payWord);
            if (!encryPayword.equals(customer.getPayWord())) {
                if (StringUtils.isBlank(customer.getPayWord())) {
                    return RestResult.fail("not_set_pay_word", null, "1");
                } else {
                    return RestResult.fail("pay_word_error");
                }
            }
        }

        OrderMain orderMain = orderMainService.getByPayCode(payCode);
        // 校验订单状态
        if(!FunctionUtils.isEquals(orderMain.getOrderStatus(), OrderStatusEnums.USING.getId())) {
            // 订单不是在使用中，借用失败
            return RestResult.fail("buy_power_fail");
        }
        // 计算一共使用了多少个小时
        orderMain.setReturnTime(DateUtil.currentDate());
        // 操作时长-余额
        int hour = DateUtil.dateDiffHour(orderMain.getRentTime(), orderMain.getReturnTime());
        // 查询是否有时长
        CAccount account = cAccountService.getByCid(orderMain.getCid());
        if (account == null) {
            return RestResult.fail("fail");
        }
        // 判断是否交了押金
        if (!FunctionUtils.isEquals(account.getRechargeDeposit(), StaticUtils.STATUS_YES)) {
            return RestResult.fail("no_deposit");
        }

        BDevice device = bDeviceService.getByCode(orderMain.getDeviceId());
        if (device == null) {
            return RestResult.fail("data_exception");
        }
        String msg = "";
        // 一小时多少钱
        BigDecimal value = device.getTerminalPrice();
        // 实际扣除时长
        Integer realHour = hour;
        // 实际扣除余额
        BigDecimal payMoney = BigDecimal.ZERO;
        // 有时长，先扣时长
        // 时长足够
        if (!(account.getHour() > 0 && account.getHour() >= hour)) {
            // 时长不足——先有多少扣多少
            realHour = account.getHour();
            // 扣除时长后计算应该扣多少余额
            payMoney = FunctionUtils.mul(value, new BigDecimal(hour - realHour), 2);
        }

        CPayLog log = new CPayLog();
        log.setCid(cid);
        log.setPayType(payType);
        log.setPayChannel(payChannel);
        log.setPayMoney(payMoney);
        log.setHandleType(HandleTypeEnums.BUY_POWER.getId());
        log.setServiceType(ServiceEnums.BUY_POWER.getId());
        log.setPayCode(orderMain.getPayCode());
        log.setParams(realHour.toString());
        boolean flag = cPayLogService.save(log);
        if (!flag) {
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
        }
        // 计算金额完毕——如果实际支付金额为0也就是使用时长抵扣了
        if (BigDecimal.ZERO.compareTo(payMoney) == 0) {
            // 时长充足——不应该进入该方法
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_fail"));
        } else if (BigDecimal.ZERO.compareTo(payMoney) > 0) {
            // 支付失败，支付金额有误
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_fail"));
        }
        return payService.handleSign(log);
    }

    @Override
    public RestResult handleValidHourBuyPower(Integer cid, String orderCode, Integer payChannel) {
        CCustomer customer = customerService.getById(cid);
        if (customer == null) {
            return RestResult.fail("register_user_empty");
        }

        OrderMain orderMain = orderMainService.getByOrderCode(orderCode);
        // 校验订单状态
        if(!FunctionUtils.isEquals(orderMain.getOrderStatus(), OrderStatusEnums.USING.getId())) {
            // 订单不是在使用中，借用失败
            return RestResult.fail("buy_power_fail");
        }
        // 计算一共使用了多少个小时
        orderMain.setReturnTime(DateUtil.currentDate());
        // 操作时长-余额
        int hour = DateUtil.dateDiffHour(orderMain.getRentTime(), orderMain.getReturnTime());
        // 查询是否有时长
        CAccount account = cAccountService.getByCid(orderMain.getCid());
        if (account == null) {
            return RestResult.fail("fail");
        }
        // 判断是否交了押金
        if (!FunctionUtils.isEquals(account.getRechargeDeposit(), StaticUtils.STATUS_YES)) {
            return RestResult.fail("no_deposit");
        }
        BDevice device = bDeviceService.getByCode(orderMain.getDeviceId());
        if (device == null) {
            return RestResult.fail("data_exception");
        }
        // 一小时多少钱
        BigDecimal value = device.getTerminalPrice();
        // 实际扣除时长
        Integer realHour = hour;
        // 实际扣除余额
        BigDecimal payMoney = BigDecimal.ZERO;
        // 有时长，先扣时长
        // 时长足够
        if (!(account.getHour() > 0 && account.getHour() >= hour)) {
            // 时长不足——先有多少扣多少
            realHour = account.getHour();
            // 扣除时长后计算应该扣多少余额
            payMoney = FunctionUtils.mul(value, new BigDecimal(hour - realHour), 2);
        }

        // 计算金额完毕——如果实际支付金额为0也就是使用时长抵扣了
        if (BigDecimal.ZERO.compareTo(payMoney) == 0) {
            // 时长充足——进入购买充电宝回调方法
            CPayLog log = new CPayLog();
            log.setCid(cid);
            log.setPayType(PayTypeEnums.MONEY.getId());
            log.setPayChannel(payChannel);
            log.setPayMoney(payMoney);
            log.setHandleType(HandleTypeEnums.BUY_POWER.getId());
            log.setServiceType(ServiceEnums.BUY_POWER.getId());
            log.setPayCode(orderMain.getPayCode());
            log.setParams(realHour.toString());
            boolean flag = cPayLogService.save(log);
            if (!flag) {
                throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
            }
            payService.handlePayCallBack(log.getPayCode(), null);
            return RestResult.success("pay_success", orderMain.getPayCode(), "1");
        } else if (BigDecimal.ZERO.compareTo(payMoney) > 0) {
            // 支付失败，支付金额有误
            throw new ThrowJsonException("pay_fail");
        } else {
            // 时长不足
            return RestResult.success("success", orderMain.getPayCode(), "0");
        }
    }
}
