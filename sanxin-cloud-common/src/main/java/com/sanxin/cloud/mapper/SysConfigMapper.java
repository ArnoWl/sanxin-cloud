package com.sanxin.cloud.mapper;

import com.sanxin.cloud.entity.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author arno
 * @since 2019-08-23
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    List<SysConfig> getOneTest();
}
