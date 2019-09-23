package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.common.OrderMapping;
import com.sanxin.cloud.app.api.service.OrderService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.OrderBusDetailVo;
import com.sanxin.cloud.dto.OrderBusVo;
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
     * 查询订单列表
     * @param page
     * @param key 查询条件暂时没用-图上不知道查什么
     * @return
     */
    @GetMapping(value = OrderMapping.QUERY_BUSINESS_ORDER_LIST)
    public RestResult queryBusinessOrderList(SPage<OrderMain> page, String key, Integer orderStatus) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        businessService.validById(bid);
        OrderMain orderMain = new OrderMain();
        orderMain.setBid(bid);
        orderMain.setOrderStatus(orderStatus);
        // TODO key没做操作
        orderMain.setKey(key);
        SPage<OrderBusVo> pageInfo = orderService.queryBusinessOrderList(page, orderMain);
        return RestResult.success("", pageInfo);
    }

    /**
     * 查询加盟商订单详情
     * @param orderCode
     * @return
     */
    @GetMapping(value = OrderMapping.GET_BUSINESS_ORDER_DETAIL)
    public RestResult getBusinessOrderDetail(String orderCode) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        OrderBusDetailVo vo = orderService.getBusinessOrderDetail(bid, orderCode);
        return RestResult.success("", vo);
    }
}
