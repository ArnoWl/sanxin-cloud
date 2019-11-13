package com.sanxin.cloud.admin.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.entity.SysRoles;
import com.sanxin.cloud.entity.SysUser;
import com.sanxin.cloud.enums.RandNumType;
import com.sanxin.cloud.exception.LoginOutException;
import com.sanxin.cloud.service.SysConfigService;
import com.sanxin.cloud.service.SysRolesService;
import com.sanxin.cloud.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-23
 */
@Service
public class LoginService {


    @Autowired
    public SysConfigService sysConfigService;
    @Autowired
    public SysUserService sysUserService;
    @Autowired
    public RedisUtilsService redisUtilsService;
    @Autowired
    public SysRolesService sysRolesService;
    @Autowired
    public UtilService utilService;


    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    public RestResult login(String username ,String password){
        if(StringUtils.isEmpty(username)){
            return RestResult.fail("login_empty");
        }
        if(StringUtils.isEmpty(password)){
            return RestResult.fail("login_password_empty");
        }
        //先清除账号残留的token
        loginOutByUsername(username);
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("login",username);
        SysUser sysUser=sysUserService.getOne(wrapper);
        if(sysUser==null){
            return RestResult.fail("login_not_found");
        }
        if(FunctionUtils.isEquals(StaticUtils.STATUS_NO,sysUser.getStatus())){
            return RestResult.fail("login_frozen");
        }
        String pwd= PwdEncode.encodePwd(password);
        if(!pwd.equals(sysUser.getPassword())){
            return RestResult.fail("login_error");
        }
        String key=username;
        String token= PwdEncode.encodePwd(RandNumUtils.get(RandNumType.NUMBER_LETTER_SYMBOL,16));
        long max_time=2*60*60;//两个小时有效
        redisUtilsService.setKey(token,key,max_time);
        redisUtilsService.setKey(username+"_token",token,max_time);
        return RestResult.success("Success",token);
    }


    /**
     * 通过token退出
     * @param token
     */
    public void loginOutByToken(String token){
        String key=redisUtilsService.getKey(token);
        if(!StringUtils.isBlank(key)){
            redisUtilsService.deleteKey(token);
            redisUtilsService.deleteKey(key);
            utilService.removeMenus(token);
        }
    }

    /**
     * 通过token退出
     * @param username
     */
    public void loginOutByUsername(String username){
        String key=username+"_token";
        String token=redisUtilsService.getKey(username+"_token");
        if(!StringUtils.isBlank(token)){
            redisUtilsService.deleteKey(token);
            redisUtilsService.deleteKey(key);
            utilService.removeMenus(token);
        }
    }

    /**
     * 获取会员信息
     * @param token
     * @return
     */
    public RestResult getUserInfo(String token){
        if(StringUtils.isBlank(token)){
            throw new LoginOutException("token is empty");
        }
        String key=redisUtilsService.getKey(token);
        if(StringUtils.isEmpty(key)){
            throw new LoginOutException("token is timeout");
        }
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("login",key);
        SysUser sysUser=sysUserService.getOne(wrapper);
        if(sysUser==null){
            throw new LoginOutException("login_not_found");
        }
        if(FunctionUtils.isEquals(StaticUtils.STATUS_NO,sysUser.getStatus())){
            throw new LoginOutException("login_frozen");
        }
        SysRoles sysRoles=sysRolesService.getById(sysUser.getRoleid());
        if(sysRoles==null || FunctionUtils.isEquals(StaticUtils.STATUS_NO,sysRoles.getStatus())){
            throw new LoginOutException("login_user_not_found");
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("roleid",String.valueOf(sysUser.getRoleid()));
        if(!StringUtils.isEmpty(sysUser.getHeadurl())){
            jsonObject.put("headurl",sysUser.getHeadurl());
        }else{
            jsonObject.put("headurl","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        }
        jsonObject.put("nickname",sysUser.getName());
        jsonObject.put("introduction",sysRoles.getName());
        return RestResult.success("success",jsonObject);
    }

    /**
     * 修改操作员头像
     * @param token
     * @return
     */
    public RestResult updateHeadUrl(String token, String headurl) {
        if(StringUtils.isBlank(token)){
            throw new LoginOutException("token is empty");
        }
        String key=redisUtilsService.getKey(token);
        if(StringUtils.isEmpty(key)){
            throw new LoginOutException("token is timeout");
        }
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("login",key);
        SysUser sysUser=sysUserService.getOne(wrapper);
        if(sysUser==null){
            throw new LoginOutException("login_not_found");
        }
        if(FunctionUtils.isEquals(StaticUtils.STATUS_NO,sysUser.getStatus())){
            throw new LoginOutException("login_frozen");
        }
        sysUser.setHeadurl(headurl);
        boolean result = sysUserService.updateById(sysUser);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 修改用户密码
     * @param token
     * @return
     */
    public RestResult updatePassword(String token, String password) {
        String key=redisUtilsService.getKey(token);
        if(StringUtils.isEmpty(key)){
            throw new LoginOutException("token is timeout");
        }
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("login",key);
        SysUser sysUser=sysUserService.getOne(wrapper);
        if(sysUser==null){
            throw new LoginOutException("login_not_found");
        }
        if(FunctionUtils.isEquals(StaticUtils.STATUS_NO,sysUser.getStatus())){
            throw new LoginOutException("login_frozen");
        }
        sysUser.setPassword(password);
        boolean result = sysUserService.updateById(sysUser);
        if (result) {
            redisUtilsService.deleteKey(token);
            redisUtilsService.deleteKey(key);
            utilService.removeMenus(token);
            return RestResult.success("update_password_success");
        }
        return RestResult.fail("fail");
    }
}
