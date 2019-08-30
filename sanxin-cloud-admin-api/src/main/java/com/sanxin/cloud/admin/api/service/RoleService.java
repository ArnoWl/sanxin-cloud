package com.sanxin.cloud.admin.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.SysMenus;
import com.sanxin.cloud.entity.SysRoles;
import com.sanxin.cloud.entity.SysUser;
import com.sanxin.cloud.service.SysMenusService;
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
    @Autowired
    private SysMenusService sysMenusService;
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
    public RestResult queryRoleList(SysRoles roles) {
        List<SysRoles> list=sysRolesService.queryRoles(roles);
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

    /**
     * 开启、关闭
     * @param id
     * @param status
     * @return
     */
    public RestResult updateUserStatus(Integer id, Integer status) {
        SysUser sysUser=new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(status);
        boolean flag= sysUserService.updateById(sysUser);
        return RestResult.result(flag,"更新失败");
    }

    /**
     * 查询菜单
     * @param roleid
     * @param language
     * @return
     */
    public RestResult queryMenums(Integer roleid, String language) {
        QueryWrapper<SysMenus> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        wrapper.orderByAsc("sort");
        List<SysMenus> list=sysMenusService.list(wrapper);
        for (SysMenus m:list){

        }
        return null;
    }

    /**
     * 查询当前角色菜单
     * @param roleid
     * @param language
     * @return
     */
    public RestResult queryMyroleMenus(Integer roleid, String language) {
        SysRoles sysRoles=sysRolesService.getById(roleid);
        if(sysRoles==null || FunctionUtils.isEquals(StaticUtils.SATUS_NO,sysRoles.getStatus())){
            return RestResult.fail("角色不存在,或被关闭");
        }
        if(StringUtils.isEmpty(sysRoles.getMenuIds())){
            return RestResult.fail("您未具备权限");
        }
        List<Integer> menuids=FunctionUtils.getIntegerList(sysRoles.getMenuIds().split(","));
        QueryWrapper<SysMenus> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",0).in("id",menuids);
        wrapper.orderByAsc("sort");
        List<SysMenus> list=sysMenusService.list(wrapper);
        for(SysMenus l:list){
            QueryWrapper<SysMenus> childwrapper=new QueryWrapper<>();
            childwrapper.eq("parent_id",l.getId()).in("id",menuids).eq("type","1");
            childwrapper.orderByAsc("sort");
            JSONObject object=JSONObject.parseObject(l.getName());
            l.setMenuname(object.getString(language));
            List<SysMenus> childList=sysMenusService.list(childwrapper);
            for(SysMenus c:childList){
                JSONObject jsonObject=JSONObject.parseObject(c.getName());
                c.setMenuname(jsonObject.getString(language));
            }
            l.setChildList(childList);
        }
        return RestResult.success("Success",list);
    }
}
