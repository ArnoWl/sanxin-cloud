package com.sanxin.cloud.mapper;

import com.sanxin.cloud.entity.CPushLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author Arno
 * @since 2019-09-17
 */
public interface CPushLogMapper extends BaseMapper<CPushLog> {

    /**
     * 根据用户id批量更新已读
     * @param cid
     * @return
     */
    boolean updateList(Integer cid);
}
