package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.entity.SysUser;

/**
 * <p>
 * 平台用户表 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
public interface SysUserService extends IService<SysUser> {

    Page<SysUser> querySysUserList(Page<SysUser> page, SysUser user);
}
