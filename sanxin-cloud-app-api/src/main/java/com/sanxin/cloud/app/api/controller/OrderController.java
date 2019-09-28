package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.common.OrderMapping;
import com.sanxin.cloud.app.api.service.OrderService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.OrderBusDetailVo;
import com.sanxin.cloud.dto.OrderBusVo;
import com.sanxin.cloud.dto.OrderUserDetailVo;
import com.sanxin.cloud.dto.OrderUserVo;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.service.BBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单Controller
 * @author xiaoky
 * @date 2019-09-21
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private BBusinessService businessService;
    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表(加盟商)
     * @param page
     * @param key 模糊查询订单编号
     * @return
     */
    @RequestMapping(value = OrderMapping.QUERY_BUSINESS_ORDER_LIST)
    public RestResult queryBusinessOrderList(SPage<OrderMain> page, String key, Integer orderStatus) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        businessService.validById(bid);
        OrderMain orderMain = new OrderMain();
        orderMain.setBid(bid);
        orderMain.setOrderStatus(orderStatus);
        orderMain.setKey(key);
        SPage<OrderBusVo> pageInfo = orderService.queryBusinessOrderList(page, orderMain);
        return RestResult.success("", pageInfo);
    }

    /**
     * 查询加盟商订单详情(用户)
     * @param orderCode
     * @return
     */
    @RequestMapping(value = OrderMapping.GET_BUSINESS_ORDER_DETAIL)
    public RestResult getBusinessOrderDetail(String orderCode) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginBid(token);
        OrderBusDetailVo vo = orderService.getBusinessOrderDetail(bid, orderCode);
        return RestResult.success("", vo);
    }

    /**
     * 查询用户订单列表
     * @param page
     * @param orderStatus 订单状态
     * @return
     */
    @RequestMapping(value = OrderMapping.QUERY_USER_ORDER_LIST)
    public RestResult queryUserOrderList(SPage<OrderMain> page, Integer orderStatus) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        OrderMain orderMain = new OrderMain();
        orderMain.setCid(cid);
        orderMain.setOrderStatus(orderStatus);
        SPage<OrderUserVo> pageInfo = orderService.queryUserOrderList(page, orderMain);
        return RestResult.success("", pageInfo);
    }

    /**
     * 查询用户订单详情
     * @param orderCode
     * @return
     */
    @RequestMapping(value = OrderMapping.GET_USER_ORDER_DETAIL)
    public RestResult getUserOrderDetail(String orderCode) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        OrderUserDetailVo vo = orderService.getUserOrderDetail(cid, orderCode);
        return RestResult.success("", vo);
    }
}
