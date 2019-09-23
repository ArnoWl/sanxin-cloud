package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.OrderMainMapper;
import com.sanxin.cloud.service.OrderMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
