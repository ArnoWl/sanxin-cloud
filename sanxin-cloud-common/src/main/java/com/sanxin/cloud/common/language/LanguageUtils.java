package com.sanxin.cloud.common.language;

import com.sanxin.cloud.enums.LanguageEnums;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * 获取国际化语言提示信息工具类
 * @author xiaoky
 * @date 2019-09-07
 */
public class LanguageUtils {

    private static String file = "language";

    private static String commonFile = "config.language";

    private HttpServletRequest request = null;

    private static LanguageUtils languageUtils = null;
    public static LanguageUtils getInstance(){
        if(languageUtils == null){
            synchronized(LanguageUtils.class){
                if(languageUtils == null){
                    languageUtils = new LanguageUtils();
                }
            }
            return languageUtils;
        }
        return languageUtils;
    }

    /**
     * 获取国际化信息
     * @param code  配置文件中的key
     * @return
     */
    public static String getMessage(String code) {
        String result = "";
        try {
            String fileName = getFileName();
            ResourceBundle rb = ResourceBundle.getBundle(fileName);
            result = rb.getString(code);
        } catch (Exception e) {
            try {
                String fileName = getCommonFileName();
                ResourceBundle rb = ResourceBundle.getBundle(fileName);
                result = rb.getString(code);
            } catch (Exception ex) {
                result = code;
            }
        }
        return result;
    }
    /**
     * 获取国际化信息
     * @param code 配置文件中的key
     * @param params 参数信息
     * @return
     */
    public static String getMessage(String code, String[] params) {
        String result = "";
        try {
            String fileName = getFileName();
            ResourceBundle rb = ResourceBundle.getBundle(fileName);
            result = rb.getString(code);
            result = MessageFormat.format(result, params);
        } catch (Exception e) {
            try {
                String fileName = getCommonFileName();
                ResourceBundle rb = ResourceBundle.getBundle(fileName);
                result = rb.getString(code);
                result = MessageFormat.format(result, params);
            } catch (Exception ex) {
                result = code;
            }
        }
        return result;
    }

    /**
     * 根据请求头获取文件名
     * @return
     */
    private static String getFileName () {
        String fileName = file;
        String languageToken = getLanguage();
        if (StringUtils.isNotBlank(languageToken)) {
            fileName += "_" + languageToken;
        } else {
            // 默认中文
            fileName += "_CN";
        }
        return fileName;
    }

    /**
     * 公共层找国际化文件
     * @return
     */
    private static String getCommonFileName () {
        String fileName = commonFile;
        String languageToken = getLanguage();
        if (StringUtils.isNotBlank(languageToken)) {
            fileName += "_" + languageToken;
        } else {
            // 默认中文
            fileName += "_CN";
        }
        return fileName;
    }

    /**
     * 获取languageToken
     * @return
     */
    public static String getLanguage() {
        String languageToken = getInstance().getRequest().getHeader("languageToken");
        if (StringUtils.isEmpty(languageToken)) {
            languageToken = LanguageEnums.CN.name();
        }
        languageToken = languageToken.toUpperCase();
        return languageToken;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
