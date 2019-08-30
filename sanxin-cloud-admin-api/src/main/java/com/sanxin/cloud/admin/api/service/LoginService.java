package com.sanxin.cloud.admin.api.service;

import com.alibaba.fastjson.JSONArray;
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


    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    public RestResult login(String username ,String password){
        if(StringUtils.isEmpty(username)){
            return RestResult.fail("请输入账号");
        }
        if(StringUtils.isEmpty(password)){
            return RestResult.fail("请输入密码");
        }
        //先清除账号残留的token
        loginOutByUsername(username);
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("login",username);
        SysUser sysUser=sysUserService.getOne(wrapper);
        if(sysUser==null){
            return RestResult.fail("账户不存在");
        }
        if(FunctionUtils.isEquals(StaticUtils.SATUS_NO,sysUser.getStatus())){
            return RestResult.fail("账户被冻结");
        }
        String pwd= PwdEncode.encodePwd(password);
        if(!pwd.equals(sysUser.getPassword())){
            return RestResult.fail("账号或密码错误");
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
        }
    }

    /**
     * 获取会员信息
     * @param token
     * @return
     */
    public RestResult getUserInfo(String token){
        if(StringUtils.isBlank(token)){
            return RestResult.fail("1001","token is empty",null);
        }
        String key=redisUtilsService.getKey(token);
        if(StringUtils.isEmpty(key)){
            return RestResult.fail("1001","token is timeout",null);
        }
        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        wrapper.eq("login",key);
        SysUser sysUser=sysUserService.getOne(wrapper);
        if(sysUser==null){
            return RestResult.fail("1001","账户不存在",null);
        }
        if(FunctionUtils.isEquals(StaticUtils.SATUS_NO,sysUser.getStatus())){
            return RestResult.fail("1001","账户被冻结",null);
        }
        SysRoles sysRoles=sysRolesService.getById(sysUser.getRoleid());
        if(sysRoles==null || FunctionUtils.isEquals(StaticUtils.SATUS_NO,sysRoles.getStatus())){
            return RestResult.fail("1001","角色不存在或被冻结",null);
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
        return RestResult.success("Success",jsonObject);
    }
}
