package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.entity.SysUser;
import com.sanxin.cloud.mapper.SysUserMapper;
import com.sanxin.cloud.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 平台用户表 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public Page<SysUser> querySysUserList(Page<SysUser> page, SysUser user) {
        return baseMapper.querySysUserList(page,user);
    }
}
