package com.sanxin.cloud.config.redis;

import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.entity.InfoAli;
import com.sanxin.cloud.exception.ThrowJsonException;
import org.apache.commons.lang3.StringUtils;

/**
 * redis工具类
 *
 * @author arno
 */
public class RedisUtils {

    public static RedisUtils redisUtils = null;


    /**
     * 凭证有效时间
     */
    public static final long EXPIRES_IN = 3600;

    public static RedisUtils getInstance() {
        if (redisUtils == null) {
            synchronized (RedisUtils.class) {
                if (redisUtils == null) {
                    redisUtils = new RedisUtils();
                }
            }
        }
        return redisUtils;
    }

    /************************支付宝模块*****************************/

    /**
     * 设置支付宝信息
     */
    public void setAliInfo(InfoAli infos) {
        String obj = JSONObject.toJSONString(infos);
        getRedisUtilsService().setKey("info_ali_", obj);
    }

    /**
     * 获取支付宝信息
     */
    public InfoAli getAliInfo() {
        String obj = getRedisUtilsService().getKey("info_ali_");
        if (StringUtils.isBlank(obj)) {
            throw new ThrowJsonException("ALIPAY IS Null");
        }
        InfoAli infos = JSONObject.parseObject(obj, InfoAli.class);
        return infos;
    }

    /**
     * 注入redis
     */
    private RedisUtilsService getRedisUtilsService() {
        return SpringBeanFactoryUtils.getApplicationContext().getBean(RedisUtilsService.class);
    }
}
