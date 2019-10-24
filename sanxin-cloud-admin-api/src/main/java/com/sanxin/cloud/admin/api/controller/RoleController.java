package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.admin.api.service.RoleService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.SysRoles;
import com.sanxin.cloud.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-26
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;
    /**
     * 查询操作员
     * @return
     */
    @RequestMapping("/role/querySysUserList")
    public RestResult querySysUserList(SPage<SysUser> page, SysUser user){
        RestResult result=roleService.querySysUserList(page,user);
        return result;
    }

    /**
     * 查询角色
     * @return
     */
    @RequestMapping("/role/queryRoleList")
    public RestResult queryRoleList(SysRoles roles){
        RestResult result=roleService.queryRoleList(roles);
        return result;
    }

    /**
     * 添加/编辑 操作员
     * @param user
     * @return
     */
    @RequestMapping("/role/addUser")
    public RestResult addUser(SysUser user){
        RestResult result=roleService.addUser(user);
        return result;
    }

    /**
     * 开启/关闭
     * @return
     */
    @RequestMapping("/role/updateUserStatus")
    public RestResult updateUserStatus(Integer id,Integer status){
        RestResult result=roleService.updateUserStatus(id,status);
        return result;
    }



    /**
     * 查询所有菜单
     * @param roleid
     * @return
     */
    @RequestMapping("/role/queryMenus")
    public RestResult queryMenus(String roleid){
        Integer role=null;
        if(!StringUtils.isEmpty(roleid)){
            role=Integer.parseInt(roleid);
        }
        RestResult result=roleService.queryMenus(role, BaseUtil.getLanguage());
        return result;
    }


    /**
     * 查询当前角色所有菜单
     * @param roleid
     * @return
     */
    @RequestMapping("/role/queryMyroleMenus")
    public RestResult queryMyroleMenus(String roleid){
        if(StringUtils.isEmpty(roleid)){
            return RestResult.fail(AdminLanguageStatic.ROLE_EMPTY);
        }
        Integer role=Integer.parseInt(roleid);
        String token=BaseUtil.getToken();
        RestResult result=roleService.queryMyroleMenus(token,role,BaseUtil.getLanguage());
        return result;
    }

    /**
     * 修改角色状态
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/role/updateRoleStatus")
    public RestResult updateRoleStatus(Integer id,Integer status){
        RestResult result=roleService.updateRoleStatus(id,status);
        return result;
    }

    /**
     * 编辑/添加角色
     * @param id
     * @param name
     * @param menuids
     * @return
     */
    @RequestMapping("/role/updateRoles")
    public RestResult updateRoles(Integer id,String name,String menuids){
        RestResult result=roleService.updateRoles(id,name,menuids);
        return result;
    }
}
