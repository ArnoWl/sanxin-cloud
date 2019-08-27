package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.sanxin.cloud.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 平台用户表 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
public interface SysUserService extends IService<SysUser> {

    List<SysUser> querySysUserList(Page<SysUser> page, SysUser user);
}
