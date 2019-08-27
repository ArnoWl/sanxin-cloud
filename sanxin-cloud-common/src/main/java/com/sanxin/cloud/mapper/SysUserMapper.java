package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.entity.SysUser;

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

    Page<SysUser> querySysUserList(IPage<SysUser> page, SysUser user);
}
