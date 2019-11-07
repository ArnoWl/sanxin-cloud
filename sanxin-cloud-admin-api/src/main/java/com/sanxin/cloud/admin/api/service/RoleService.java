package com.sanxin.cloud.admin.api.service;

import com.alibaba.fastjson.JSONArray;
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

import java.util.ArrayList;
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
    @Autowired
    private UtilService utilService;
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
            return RestResult.fail("login_empty");
        }
        if(user.getRoleid()==null){
            return RestResult.fail("user_empty");
        }
        if(user.getId()==null){
            if(StringUtils.isEmpty(user.getPassword()) ){
                return RestResult.fail("password_empty");
            }
            if(user.getPassword().length()<6 || user.getPassword().length()>20){
                return RestResult.fail("password_length_error");
            }
            user.setPassword(PwdEncode.encodePwd(user.getPassword()));
            QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
            wrapper.eq("login",user.getLogin());
            int count=sysUserService.count(wrapper);
            if(count>0){
                return RestResult.fail("login_is_existence");
            }
            sysUserService.save(user);
        }else{
            if(!StringUtils.isEmpty(user.getPassword()) ){
                if(user.getPassword().length()<6 || user.getPassword().length()>20){
                    return RestResult.fail("password_length_error");
                }
                user.setPassword(PwdEncode.encodePwd(user.getPassword()));
            }else{
                user.setPassword(null);
            }
            QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
            wrapper.eq("login",user.getLogin()).ne("id",user.getId());
            int count=sysUserService.count(wrapper);
            if(count>0){
                return RestResult.fail("login_is_existence");
            }
            sysUserService.updateById(user);
        }

        return RestResult.success("success");
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
        return RestResult.result(flag,"fail");
    }

    /**
     * 查询菜单
     * @param roleid
     * @param language
     * @return
     */
    public RestResult queryMenus(Integer roleid, String language) {
        List<Integer> menuids=null;
        if(roleid!=null){
            SysRoles sysRoles=sysRolesService.getById(roleid);
            if(sysRoles==null || FunctionUtils.isEquals(StaticUtils.STATUS_NO,sysRoles.getStatus())){
                return RestResult.fail("login_user_not_found");
            }
            if(StringUtils.isEmpty(sysRoles.getMenuIds())){
                return RestResult.fail("user_not_have_auth");
            }
            menuids=FunctionUtils.getIntegerList(sysRoles.getMenuIds().split(","));
        }
        QueryWrapper<SysMenus> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        wrapper.orderByAsc("sort");
        List<SysMenus> list=sysMenusService.list(wrapper);
        List<Integer> checkList=new ArrayList<>();
        JSONArray array =new JSONArray();
        for (SysMenus l:list){
            if(StringUtils.isEmpty(l.getName())){
                continue;
            }
            JSONObject object=JSONObject.parseObject(l.getName());

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",l.getId());
            jsonObject.put("label",object.getString(language));

            QueryWrapper<SysMenus> childwrapper=new QueryWrapper<>();
            childwrapper.eq("parent_id",l.getId());
            childwrapper.orderByAsc("type,sort");
            List<SysMenus> childList=sysMenusService.list(childwrapper);
            JSONArray childArr=new JSONArray();
            for(SysMenus c:childList){
                if(menuids!=null ){
                    if(menuids.contains(c.getId())){
                        checkList.add(c.getId());
                    }
                }
                if(StringUtils.isEmpty(c.getName())){
                    continue;
                }
                JSONObject parseObject=JSONObject.parseObject(c.getName());

                JSONObject child=new JSONObject();
                child.put("id",c.getId());
                if(FunctionUtils.isEquals(1,c.getType())){
                    child.put("label",parseObject.getString(language)+"【PAGE】");
                }else{
                    child.put("label",parseObject.getString(language)+"【BUTTON】");
                }
                childArr.add(child);
            }
            jsonObject.put("children",childArr);
            array.add(jsonObject);
        }
        JSONObject result=new JSONObject();
        result.put("menusList",array);
        result.put("checkData",checkList);
        return RestResult.success("success",result);
    }

    /**
     * 查询当前角色菜单
     * @param roleid
     * @param language
     * @return
     */
    public RestResult queryMyroleMenus(String token,Integer roleid, String language) {
        SysRoles sysRoles=sysRolesService.getById(roleid);
        if(sysRoles==null || FunctionUtils.isEquals(StaticUtils.STATUS_NO,sysRoles.getStatus())){
            return RestResult.fail("login_user_not_found");
        }
        if(StringUtils.isEmpty(sysRoles.getMenuIds())){
            return RestResult.fail("user_not_have_auth");
        }
        List<SysMenus> list=utilService.getMenus(token);
        if(list==null || list.size()<1){
            List<Integer> menuids=FunctionUtils.getIntegerList(sysRoles.getMenuIds().split(","));
            QueryWrapper<SysMenus> wrapper=new QueryWrapper<>();
            wrapper.eq("parent_id",0).in("id",menuids);
            wrapper.orderByAsc("sort");
            list=sysMenusService.list(wrapper);


            for(SysMenus l:list){
                if(StringUtils.isEmpty(l.getName())){
                    continue;
                }
                JSONObject object=JSONObject.parseObject(l.getName());
                l.setMenuname(object.getString(language));

                QueryWrapper<SysMenus> childwrapper=new QueryWrapper<>();
                childwrapper.eq("parent_id",l.getId()).in("id",menuids);
                childwrapper.orderByAsc("sort");
                List<SysMenus> childList=sysMenusService.list(childwrapper);
                for(SysMenus c:childList){

                    if(StringUtils.isEmpty(c.getName())){
                        continue;
                    }
                    JSONObject jsonObject=JSONObject.parseObject(c.getName());
                    c.setMenuname(jsonObject.getString(language));
                }
                l.setChildList(childList);
            }
            //第一次设置菜单缓存
            utilService.setMenus(token,list);
        }

        return RestResult.success("Success",list);
    }


    public RestResult updateRoleStatus(Integer id, Integer status) {
        SysRoles sysRoles=new SysRoles();
        sysRoles.setId(id);
        sysRoles.setStatus(status);
        boolean flag=sysRolesService.updateById(sysRoles);
        return RestResult.result(flag);
    }

    public RestResult updateRoles(Integer id,String name, String menuids) {
        if(StringUtils.isEmpty(name)){
            return RestResult.fail("user_name_empty");
        }
        if(StringUtils.isEmpty(menuids)){
            return RestResult.fail("user_menu_empty");
        }
        SysRoles sysRoles=new SysRoles();
        sysRoles.setId(id);
        sysRoles.setName(name);
        sysRoles.setMenuIds(menuids);
        boolean flag=sysRolesService.saveOrUpdate(sysRoles);
        return RestResult.result(flag);
    }
}
