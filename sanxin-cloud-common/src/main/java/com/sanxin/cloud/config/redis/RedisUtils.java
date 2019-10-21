package com.sanxin.cloud.config.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.dto.BTerminalVo;
import com.sanxin.cloud.entity.BDeviceTerminal;
import com.sanxin.cloud.entity.InfoAli;
import com.sanxin.cloud.exception.ThrowJsonException;
import jdk.nashorn.internal.ir.Terminal;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

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
     * 设置充电宝信息
     * @param boxId
     * @param list
     */
    public void setTerminalByBoxId(String boxId, List<BTerminalVo> list) {
        String obj = JSON.toJSONString(list);
        getRedisUtilsService().setKey(boxId, obj);
    }

    /**
     * 获取充电宝信息
     * @return
     */
    public List<BTerminalVo> getTerminalByBoxId(String boxId) {
        String obj = getRedisUtilsService().getKey(boxId);
        System.out.println("充电宝编号" + boxId);
        System.out.println("获取充电宝库存信息" + obj);
        List<BTerminalVo> list = JSONArray.parseArray(obj, BTerminalVo.class);
        return list;
    }

    /**
     * 获取电量最多的充电宝
     * @param boxId
     * @return
     */
    public BTerminalVo getMostCharge(String boxId, Integer index) {
        List<BTerminalVo> list = getTerminalByBoxId(boxId);
        if (list != null && list.size()>0) {
            Collections.sort(list);
            return list.get(index);
        }
        return null;
    }

    /**
     * 注入redis
     */
    private RedisUtilsService getRedisUtilsService() {
        return SpringBeanFactoryUtils.getApplicationContext().getBean(RedisUtilsService.class);
    }
}
