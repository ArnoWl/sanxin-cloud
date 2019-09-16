package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.entity.BBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据经纬度分页查询周边商铺
     * @param page
     * @param latVal
     * @param longitude
     * @param radius
     * @return
     */
    List<BBusiness> findByShops(IPage<BBusiness> page, @Param(value = "latVal") String latVal, @Param(value = "longitude") String longitude,@Param(value = "radius") Integer radius);
}
