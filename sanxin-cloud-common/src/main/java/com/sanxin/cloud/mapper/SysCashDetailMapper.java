package com.sanxin.cloud.mapper;

import com.sanxin.cloud.entity.SysCashDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 余额提现记录表 Mapper 接口
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public interface SysCashDetailMapper extends BaseMapper<SysCashDetail> {

    /**
     * 查询当前对象已提现多少次
     * @param map
     * @return
     */
    Integer getRuleNum(Map<String, Object> map);

    /**
     * 统计提现金额
     * @return
     */
    BigDecimal sumCashMoney();
}
