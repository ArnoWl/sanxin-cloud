package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.entity.SysRoles;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
public interface SysRolesService extends IService<SysRoles> {

    List<SysRoles> queryRoles(SysRoles roles);
}
