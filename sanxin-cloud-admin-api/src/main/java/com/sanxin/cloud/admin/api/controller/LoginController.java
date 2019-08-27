package com.sanxin.cloud.admin.api.controller;

import com.sanxin.cloud.admin.api.service.LoginService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-22
 */
@RestController
public class LoginController extends  BaseController{
    protected final static Logger logger = LoggerFactory.getLogger(RedisUtilsService.class);

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public RestResult login(String username,String password){
        RestResult result=loginService.login(username,password);
        return result;
    }

    @RequestMapping("/user/getInfo")
    public RestResult getInfo(){
        String token=getToken();
        RestResult result=loginService.getUserInfo(token);
        return result;
    }


    @PostMapping("/loginOut")
    public RestResult loginOut(){
        String token=getToken();
        loginService.loginOutByToken(token);
        return  RestResult.success("退出成功");
    }
}
