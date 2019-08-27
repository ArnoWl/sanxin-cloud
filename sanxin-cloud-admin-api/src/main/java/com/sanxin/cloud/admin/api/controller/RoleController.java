package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.admin.api.service.RoleService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-26
 */
@RestController
public class RoleController extends  BaseController{

    @Autowired
    private RoleService roleService;
    /**
     * 查询
     * @return
     */
    @RequestMapping("/role/querySysUserList")
    public RestResult querySysUserList(Page<SysUser> page, SysUser user){
        String token=getToken();
        RestResult result=roleService.querySysUserList(page,user);
        return result;
    }

}
