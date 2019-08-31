package com.sanxin.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.SysUser;
import org.apache.ibatis.annotations.Param;

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

    SPage<SysUser> querySysUserList(SPage<SysUser> page, @Param("user") SysUser user);
}
