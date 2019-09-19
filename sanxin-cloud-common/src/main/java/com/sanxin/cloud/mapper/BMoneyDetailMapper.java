package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.BMoneyDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 加盟商余额明细 Mapper 接口
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-19
 */
public interface BMoneyDetailMapper extends BaseMapper<BMoneyDetail> {

    /**
     * 查询商家收益
     * @param bid
     * @param type
     * @return
     */
    BigDecimal getIncome(@Param("bid") Integer bid, @Param("type") Integer type);

    /**
     * 查询商家余额明细
     * @param page
     * @param detail
     */
    Page<BMoneyDetail> queryMoneyDetailList(SPage<BMoneyDetail> page, @Param("param") BMoneyDetail detail);
}
