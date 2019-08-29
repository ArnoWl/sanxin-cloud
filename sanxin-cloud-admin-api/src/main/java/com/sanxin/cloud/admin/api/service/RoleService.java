package com.sanxin.cloud.admin.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.SysRoles;
import com.sanxin.cloud.entity.SysUser;
import com.sanxin.cloud.service.SysRolesService;
import com.sanxin.cloud.service.SysUserService;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private SysRolesService sysRolesService;
    /**
     * 查询操作员列表
     * @param user
     * @return
     */
    public RestResult querySysUserList(SPage<SysUser> page, SysUser user) {
        SPage<SysUser> list=sysUserService.querySysUserList(page,user);
        return RestResult.success("Success",list);
    }

    /**
     * 查询所有角色
     * @return
     */
    public RestResult queryRoleList() {
        List<SysRoles> list=sysRolesService.list();
        return RestResult.success("Success",list);

    }

    public RestResult addUser(SysUser user) {
        if(StringUtils.isEmpty(user.getLogin())){
            return RestResult.fail("请输入账号");
        }
        if(user.getRoleid()==null){
            return RestResult.fail("请选择角色");
        }
        if(user.getId()==null){
            if(StringUtils.isEmpty(user.getPassword()) ){
                return RestResult.fail("密码不能为空");
            }
            if(user.getPassword().length()<6 || user.getPassword().length()>20){
                return RestResult.fail("密码长度在6~20位");
            }
            user.setPassword(PwdEncode.encodePwd(user.getPassword()));
            QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
            wrapper.eq("login",user.getLogin());
            int count=sysUserService.count(wrapper);
            if(count>0){
                return RestResult.fail("账号已存在");
            }
            sysUserService.save(user);
        }else{
            if(!StringUtils.isEmpty(user.getPassword()) ){
                if(user.getPassword().length()<6 || user.getPassword().length()>20){
                    return RestResult.fail("密码长度在6~20位");
                }
                user.setPassword(PwdEncode.encodePwd(user.getPassword()));
            }
            QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
            wrapper.eq("login",user.getLogin()).ne("id",user.getId());
            int count=sysUserService.count(wrapper);
            if(count>0){
                return RestResult.fail("账号已存在");
            }
            sysUserService.updateById(user);
        }

        return RestResult.success("成功");
    }
}
