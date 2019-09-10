package com.sanxin.cloud.common.properties;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * 属性文件读取
 *
 * @author leehao
 * @date 2019-06-17
 */
public class PropertiesUtil {

    private static PropertiesUtil propertiesUtil = null;

    public static PropertiesUtil getInstance() {
        if (propertiesUtil == null) {
            synchronized (PropertiesUtil.class) {
                if (propertiesUtil == null) {
                    propertiesUtil = new PropertiesUtil();
                }
            }
        }
        return propertiesUtil;
    }

    /**
     * 获取文本
     * @param key 键
     * @param params 参数
     * @author leehao
     * @return java.lang.String
     */
    public String getVal(String key, String[] params) {
        String msg = key;
        try {
            String propertiesFiles = "base_info";
            ResourceBundle rb = ResourceBundle.getBundle(propertiesFiles);
            String res = new String(rb.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            msg = MessageFormat.format(res, params);
        } catch (Exception e) {
        }
        return msg;
    }

    /**
     * 获取文本
     * @param key 键
     * @author leehao
     * @return java.lang.String
     */
    public String getVal(String key) {
        String res = key;
        try {
            String propertiesFiles = "base_info";
            ResourceBundle rb = ResourceBundle.getBundle(propertiesFiles);
            res = new String(rb.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //res = rb.getString(key);
        } catch (Exception e) {
        }
        return res;
    }



    /**
     * 获取翻译后的文字
     *
     * @param key 建
     * @param ext 文件后缀
     * @return
     */
    public static String getVal(String key, String ext) {
        String res = key;
        try {
            String propertiesFiles = "message";
            if (StringUtils.isEmpty(ext)) {
                propertiesFiles = "message_cn";
            } else {
                propertiesFiles = propertiesFiles + ext;
            }
            ResourceBundle rb = ResourceBundle.getBundle(propertiesFiles);
            res = rb.getString(key);
        } catch (Exception e) {
        }
        return res;
    }
}
