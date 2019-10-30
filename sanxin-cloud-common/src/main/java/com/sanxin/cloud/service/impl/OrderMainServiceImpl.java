package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.enums.LoginChannelEnums;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.OrderMainMapper;
import com.sanxin.cloud.service.OrderMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单记录表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-21
 */
@Service
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements OrderMainService {

    @Autowired
    private OrderMainMapper orderMainMapper;

    /**
     * 通过订单编号查询订单
     * @param orderCode
     * @return
     */
    @Override
    public OrderMain getByOrderCode(String orderCode) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("order_code", orderCode).eq("del", StaticUtils.STATUS_NO);
        OrderMain orderMain = super.getOne(wrapper);
        if (orderMain == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_empty"));
        }
        return orderMain;
    }


    /**
     * 订单列表
     * @param page
     * @param orderMain 订单
     */
    @Override
    public IPage<OrderMain> queryOrderList(SPage<OrderMain> page, OrderMain orderMain) {
        IPage<OrderMain> pageInfo = orderMainMapper.queryOrderList(page,orderMain);
        for (OrderMain order : pageInfo.getRecords()) {
            // 支付方式
            order.setPayTypeName(PayTypeEnums.getName(order.getPayType()));
            // 订单来源
            order.setOrderFromChannel(LoginChannelEnums.getName(order.getFromChannel()));
            //订单状态
            order.setStatusName(OrderStatusEnums.getName(order.getOrderStatus()));
        }
        return page;
    }

    @Override
    public OrderMain getByPayCode(String payCode) {
        QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
        wrapper.eq("pay_code", payCode).eq("del", StaticUtils.STATUS_NO);
        OrderMain orderMain = super.getOne(wrapper);
        if (orderMain == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_empty"));
        }
        return orderMain;
    }
}
