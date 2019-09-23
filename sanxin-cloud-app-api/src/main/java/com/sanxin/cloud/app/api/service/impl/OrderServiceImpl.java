package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.OrderService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.OrderBusDetailVo;
import com.sanxin.cloud.dto.OrderBusVo;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.CCustomerService;
import com.sanxin.cloud.service.OrderMainService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单Service实现类
 * @author xiaoky
 * @date 2019-09-21
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMainService orderMainService;
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private BBusinessService bBusinessService;

    @Override
    public SPage<OrderBusVo> queryBusinessOrderList(SPage<OrderMain> page, OrderMain orderMain) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("bid", orderMain.getBid()).eq("order_status", orderMain.getOrderStatus());
        orderMainService.page(page, wrapper);
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
            // TODO 使用时长(写死了)
            vo.setUseHour("20分钟");
            // TODO 预计租金(写死了)
            vo.setEstimatedRentMoney(BigDecimal.ONE);
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
        // 校验数据
        if (!FunctionUtils.isEquals(order.getBid(), bid)) {
            throw  new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
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
        // TODO 使用时长(写死了)
        vo.setUseHour("20分钟");
        // TODO 预计租金(写死了)
        vo.setEstimatedRentMoney(BigDecimal.ONE);
        // 支付方式
        vo.setPayTypeName(PayTypeEnums.getName(order.getPayType()));
        vo.setMoney(order.getPayMoney());
        vo.setRentTime(order.getPayTime());
        vo.setReturnTime(order.getPayTime());
        vo.setAddressDetail(business.getAddressDetail());
        return vo;
    }
}
