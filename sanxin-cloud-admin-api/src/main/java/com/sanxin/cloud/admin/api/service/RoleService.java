package com.sanxin.cloud.admin.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysUser;
import com.sanxin.cloud.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-26
 */
@Service
public class RoleService {

    @Autowired
    private SysUserService sysUserService;
    /**
     * 查询操作员列表
     * @param user
     * @return
     */
    public RestResult querySysUserList(IPage<SysUser> page, SysUser user) {
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("1","1");
//        if(!StringUtils.isEmpty(user.getLogin())){
//            wrapper.and().eq("login",user.getLogin());
//        }
//        if(user.getStatus()!=null){
//            wrapper.and().eq("status",user.getStatus());
//        }
        List<SysUser> list=sysUserService.querySysUserList(page,user);
        return RestResult.success("Success",list);
    }
}
