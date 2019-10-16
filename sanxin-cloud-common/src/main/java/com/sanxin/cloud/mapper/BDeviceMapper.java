package com.sanxin.cloud.mapper;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BDevice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 店铺设备 Mapper 接口
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-06
 */
public interface BDeviceMapper extends BaseMapper<BDevice> {

    SPage<BDevice> queryDeviceList(SPage<BDevice> page, @Param("device") BDevice device);
}
