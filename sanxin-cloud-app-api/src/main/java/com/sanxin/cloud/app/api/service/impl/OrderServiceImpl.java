package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.app.api.service.OrderService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.OrderBusDetailVo;
import com.sanxin.cloud.dto.OrderBusVo;
import com.sanxin.cloud.dto.OrderUserDetailVo;
import com.sanxin.cloud.dto.OrderUserVo;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.*;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.CCustomerService;
import com.sanxin.cloud.service.OrderMainService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
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
    private BDeviceTerminalMapper deviceTerminalMapper;
    @Autowired
    private BDeviceMapper deviceMapper;
    @Autowired
    private CAccountMapper accountMapper;


    /**
     * 借充电宝
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
        BDeviceTerminal terminal = deviceTerminalMapper.selectOne(new QueryWrapper<BDeviceTerminal>().eq("terminal_id", terminalId));
        //机柜
        BDevice code = deviceMapper.selectOne(new QueryWrapper<BDevice>().eq("code", terminal.getdCode()));

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
        wrapper.eq("bid", orderMain.getBid()).eq("order_status", orderMain.getOrderStatus())
                .like(StringUtils.isNotBlank(orderMain.getKey()), "order_code", orderMain.getKey());
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
            String useHour = DateUtil.dateDiff(o.getPayTime().getTime(), endTime.getTime());
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
        String useHour = DateUtil.dateDiff(order.getPayTime().getTime(), endTime.getTime());
        vo.setUseHour(useHour);
        // 支付方式
        vo.setPayTypeName(PayTypeEnums.getName(order.getPayType()));
        vo.setMoney(order.getPayMoney());
        vo.setRentTime(order.getPayTime());
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
        wrapper.eq("cid", orderMain.getCid()).eq("order_status", orderMain.getOrderStatus());
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
            String useHour = DateUtil.dateDiff(o.getPayTime().getTime(), endTime.getTime());
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
        String useHour = DateUtil.dateDiff(order.getPayTime().getTime(), endTime.getTime());
        vo.setUseHour(useHour);
        // 支付方式
        vo.setPayTypeName(PayTypeEnums.getName(order.getPayType()));
        vo.setMoney(order.getPayMoney());
        vo.setRentTime(order.getPayTime());
        vo.setReturnTime(order.getPayTime());
        vo.setAddressDetail(order.getReturnAddr());
        vo.setPayTime(order.getPayTime());
        vo.setHour(order.getHour());
        return vo;
    }
}
