package com.sanxin.cloud.admin.api.controller;

import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.enums.LoginChannelEnums;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.CCustomerService;
import com.sanxin.cloud.service.OrderMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMainService orderMainService;
    @Autowired
    private CCustomerService cCustomerService;
    @Autowired
    private BBusinessService bBusinessService;

    /**
     * 查询订单列表
     * @param orderMain 订单
     * @return
     */
    @GetMapping(value = "/list")
    public RestResult queryOrderList(SPage<OrderMain> page, OrderMain orderMain) {
        // 查询未删除的订单
        orderMain.setDel(StaticUtils.STATUS_NO);
        orderMainService.queryOrderList(page, orderMain);
        return RestResult.success("", page);
    }

    @GetMapping(value = "/detail")
    public RestResult queryOrderDetail(Integer id) {
        OrderMain orderMain = orderMainService.getById(id);
        if(orderMain != null){
            CCustomer customer = cCustomerService.getById(orderMain.getCid());
            if(customer != null){
                orderMain.setcNickName(customer.getNickName());
                orderMain.setPhone(customer.getPhone());
            }
            if(orderMain.getBid() != null){
                BBusiness business = bBusinessService.getById(orderMain.getBid());
                if(business != null){
                    orderMain.setbNickName(business.getNickName());
                }
            }
        }
        // 支付方式
        orderMain.setPayTypeName(PayTypeEnums.getName(orderMain.getPayType()));
        // 订单来源
        orderMain.setOrderFromChannel(LoginChannelEnums.getName(orderMain.getFromChannel()));
        //订单状态
        orderMain.setStatusName(OrderStatusEnums.getName(orderMain.getOrderStatus()));
        return RestResult.success("", orderMain);
    }
}
