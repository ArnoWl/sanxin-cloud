package com.sanxin.cloud.admin.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-26
 */
@Component
public class BaseController {

    @Autowired
    private HttpServletRequest request;


    /**
     * 获取token
     * @return
     */
    public String getToken(){
        String token=request.getHeader("sanxinToken");
        return token;
    }
}
