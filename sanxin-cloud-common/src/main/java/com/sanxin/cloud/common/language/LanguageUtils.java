package com.sanxin.cloud.common.language;

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

    private static HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

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
            System.out.println(e.getMessage());
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
        }
        return result;
    }

    /**
     * 根据请求头获取文件名
     * @return
     */
    private static String getFileName () {
        String fileName = file;
        String languageToken = request.getHeader("languageToken");
        if (StringUtils.isNotBlank(languageToken)) {
            fileName += "_" + languageToken;
        }
        return fileName;
    }

}
