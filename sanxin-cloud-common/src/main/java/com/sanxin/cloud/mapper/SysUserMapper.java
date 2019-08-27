package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.sanxin.cloud.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 平台用户表 Mapper 接口
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> querySysUserList(Page<SysUser> page, SysUser user);
}
