package com.sanxin.cloud.common;

import com.sanxin.cloud.enums.LanguageEnums;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取语言、token等工具类
 * @author xiaoky
 * @date 2019-09-16
 */
public class BaseUtil {

    private HttpServletRequest request = null;

    private static BaseUtil baseUtil = null;
    public static BaseUtil getInstance(){
        if(baseUtil == null){
            synchronized(BaseUtil.class){
                if(baseUtil == null){
                    baseUtil = new BaseUtil();
                }
            }
            return baseUtil;
        }
        return baseUtil;
    }

    /**
     * 获取token
     * @return
     */
    public static String getUserToken() {
        String token = getInstance().getRequest().getHeader("userToken");
        return token;
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken() {
        String token = getInstance().getRequest().getHeader("sanxinToken");
        return token;
    }

    /**
     * 获取 语言
     * @return
     */
    public static String getLanguage() {
        String language = getInstance().getRequest().getHeader("languageToken");
        if (StringUtils.isEmpty(language) || "undefined".equals(language)) {
            language = LanguageEnums.CN.name();
        }
        return language;
    }

    /**
     * 获取regId
     * @return
     */
    public String getRegId() {
        String token = request.getHeader("userId");
        return token;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
