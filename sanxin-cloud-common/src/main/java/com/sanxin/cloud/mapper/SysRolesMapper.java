package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanxin.cloud.entity.SysRoles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
public interface SysRolesMapper extends BaseMapper<SysRoles> {

    List<SysRoles> queryRoles(@Param("role")SysRoles roles);
}
