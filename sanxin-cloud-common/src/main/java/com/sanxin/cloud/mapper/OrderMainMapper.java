package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.OrderMain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单记录表 Mapper 接口
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-21
 */
public interface OrderMainMapper extends BaseMapper<OrderMain> {

    /**
     * 多条件查询订单
     * @param orderMain
     * @return
     */
    IPage<OrderMain> queryOrderList(SPage<OrderMain> page, @Param("order") OrderMain orderMain);

    /**
     * 统计订单金额
     * @return
     */
    BigDecimal sumOrderMoney();
}
