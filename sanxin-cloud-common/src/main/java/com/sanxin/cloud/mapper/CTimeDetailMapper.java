package com.sanxin.cloud.mapper;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.entity.CTimeDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户时长明细表 Mapper 接口
 * </p>
 *
 * @author Arno
 * @since 2019-09-20
 */
public interface CTimeDetailMapper extends BaseMapper<CTimeDetail> {

    /**
     * 用户时长明细
     * @param page
     * @param cid
     * @return
     */
    SPage<CTimeDetail> queryTimeDetail(SPage<CTimeDetail> page,@Param("cid") Integer cid);
}
