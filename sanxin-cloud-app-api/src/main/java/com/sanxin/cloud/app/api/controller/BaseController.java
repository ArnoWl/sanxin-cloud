package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.enums.LanguageEnums;
import org.apache.commons.lang.StringUtils;
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
    public String getUserToken(){
        String token=request.getHeader("userToken");
        return token;
    }

    /**
     * 获取regId
     * @return
     */
    public String getRegId(){
        String token=request.getHeader("userId");
        return token;
    }

    /**
     * 获取 语言
     * @return
     */
    public String getLanguage(){
        String language=request.getHeader("languageToken");
        if(StringUtils.isEmpty(language) || "undefined".equals(language) ){
            language= LanguageEnums.CN.name();
        }
        return language;
    }
}
