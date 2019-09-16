package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.entity.BBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
public interface BBusinessMapper extends BaseMapper<BBusiness> {

    List<BBusiness> findByShops(IPage<BBusiness> page, String latVal, String longitude, Integer radius, String province, String city, String district);
}
