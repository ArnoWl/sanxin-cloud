package com.sanxin.cloud.mapper;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CTimeDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户时长明细表 Mapper 接口
 * </p>
 *
 * @author Arno
 * @since 2019-09-27
 */
public interface CTimeDetailMapper extends BaseMapper<CTimeDetail> {

    SPage<CTimeDetail> queryTimeDetail(SPage<CTimeDetail> page, Integer cid);
}
