package com.sanxin.cloud.admin.api.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.service.SysRolesService;
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


//    @Autowired
//    public ISysConfigService iSysConfigService;
//    @Autowired
//    public ISysUserService iSysUserService;
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
//        System.out.print("shul:"+iSysConfigService.queryTest().size());
//        if(StringUtils.isEmpty(username)){
//            return RestResult.fail("请输入账号");
//        }
//        if(StringUtils.isEmpty(password)){
//            return RestResult.fail("请输入密码");
//        }
//        //先清除账号残留的token
//        loginOutByUsername(username);
//        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
//        wrapper.eq("user_name",username);
//        SysUser sysUser=iSysUserService.getOne(wrapper);
//        if(sysUser==null){
//            return RestResult.fail("账户不存在");
//        }
//        if(FunctionUtils.isEquals(StaticUtils.SATUS_NO,sysUser.getStatus())){
//            return RestResult.fail("账户被冻结");
//        }
//        String pwd= PwdEncode.encodePwd(password);
//        if(!pwd.equals(sysUser.getPassword())){
//            return RestResult.fail("账号或密码错误");
//        }
//        String key=username;
//        String token= RandNumUtils.get(RandNumType.NUMBER_LETTER_SYMBOL,16);
//        long max_time=2*60*60;//两个小时有效
//        redisUtilsService.setKey(token,key,max_time);
//        redisUtilsService.setKey(username+"_token",token,max_time);
//        return RestResult.success("登陆成功",token);
        System.out.print("hahaha:"+sysRolesService.selectList(null).size());
        return null;
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
//        if(StringUtils.isBlank(token)){
//            return RestResult.fail("1001","token is empty",null);
//        }
//        String key=redisUtilsService.getKey(token);
//        if(StringUtils.isEmpty(key)){
//            return RestResult.fail("1001","token is timeout",null);
//        }
//        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
//        wrapper.eq("user_name",key);
//        SysUser sysUser=iSysUserService.getOne(wrapper);
//        if(sysUser==null){
//            return RestResult.fail("1001","账户不存在",null);
//        }
//        if(FunctionUtils.isEquals(StaticUtils.SATUS_NO,sysUser.getStatus())){
//            return RestResult.fail("1001","账户被冻结",null);
//        }
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("roleid",sysUser.getRoleid());
//        jsonObject.put("headurl",sysUser.getHeadurl());
//        jsonObject.put("nickname",sysUser.getName());
//        return RestResult.success("获取成功",jsonObject);
        return null;
    }
}
