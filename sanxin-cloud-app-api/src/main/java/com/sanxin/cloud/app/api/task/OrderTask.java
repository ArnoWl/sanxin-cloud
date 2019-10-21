package com.sanxin.cloud.app.api.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.mapper.OrderMainMapper;
import com.sanxin.cloud.service.OrderMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author xiaoky
 * @date 2019-10-21
 */
@Component
public class OrderTask {
    @Autowired
    private OrderMainMapper orderMainMapper;

    /**
     * 定期处理过期订单
     */
    @Scheduled(cron = "* 0/1 * * * ? ")
    public void handleExpiredOrderAuto() {
        // 订单创建到过期时间
        // TODO 写死
        Integer num = 1;
        List<OrderMain> list = orderMainMapper.queryExpiredOrder(num);
        if (list != null && list.size()>0) {
           for (OrderMain order : list) {
               order.setDel(StaticUtils.STATUS_YES);
               orderMainMapper.updateById(order);
           }
        }
    }
}
