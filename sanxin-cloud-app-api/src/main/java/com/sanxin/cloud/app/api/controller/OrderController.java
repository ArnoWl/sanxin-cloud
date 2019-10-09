package com.sanxin.cloud.app.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.common.OrderMapping;
import com.sanxin.cloud.app.api.service.OrderService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.ParamCodeEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.InfoParamService;
import com.sanxin.cloud.service.OrderMainService;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.OrderBusDetailVo;
import com.sanxin.cloud.dto.OrderBusVo;
import com.sanxin.cloud.dto.OrderUserDetailVo;
import com.sanxin.cloud.dto.OrderUserVo;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.system.pay.HandleBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
    @Autowired
    private OrderMainService orderMainService;
    @Autowired
    private HandleBatteryService handleBatteryService;
    @Autowired
    private CAccountService cAccountService;
    @Autowired
    private InfoParamService infoParamService;

    /**
     * 借充电宝
     * @param terminalId 充电宝ID
     * @return
     */
    @RequestMapping(value = OrderMapping.GET_BORROW_POWER_BANK)
    public RestResult getBorrowPowerBank(String terminalId) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        orderService.getBorrowPowerBank(cid, terminalId);
        return RestResult.success("");
    }

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

    /**
     * 查询是否有未支付订单(首页点击借的时候)
     * 失败则弹出提示，成功则需判断data数据
     * @return
     */
    @RequestMapping(value = OrderMapping.QUERY_NO_COMPLETE_ORDER)
    public RestResult queryNoCompleteOrder() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid).eq("order_status", OrderStatusEnums.USING.getId());
        int orderNum = orderMainService.count(wrapper);
        if (orderNum>0) {
            return RestResult.success("success", OrderStatusEnums.USING.getId());
        } else {
            wrapper = new QueryWrapper<>();
            wrapper.eq("cid", cid).eq("order_status", OrderStatusEnums.CONFIRMED.getId());
            orderNum = orderMainService.count(wrapper);
            if (orderNum>0) {
                return RestResult.success("success", OrderStatusEnums.CONFIRMED.getId());
            }
        }
        return RestResult.success("success");
    }

    /**
     * 借充电宝扫码时判断是否交了押金
     * @return
     */
    @RequestMapping(OrderMapping.VALID_RECHARGE_DEPOSIT)
    public RestResult validRechargeDeposit() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        CAccount account = cAccountService.getByCid(cid);
        if (account == null) {
            return RestResult.success("data_exception");
        }
        return RestResult.success("success", account.getRechargeDeposit());
    }

    @RequestMapping("/test")
    public RestResult test() {
        // RestResult result = handleBatteryService.handleLendBattery(1, "RL1H081905680019", "RL1H96005635", "03", 1);
        RestResult result = handleBatteryService.handleReturnBattery("RL1H081905680019", "03", "RL1H96005635");
        return result;
    }
}
