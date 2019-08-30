package com.sanxin.cloud.admin.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.entity.SysMenus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-30
 */
@Service
public class UtilService {

    @Autowired
    private RedisUtilsService redisUtilsService;


    private static final String menus_token="adminMenusToken";


    /**
     * 设置菜单缓存
     * @param list
     */
    public void setMenus(String token,List<SysMenus> list){
        String menus=JSONArray.toJSONString(list);
        redisUtilsService.setKey(token+"_"+menus_token,menus);
    }

    /**
     * 获取菜单
     * @return
     */
    public List<SysMenus> getMenus(String token){
        String meuns=redisUtilsService.getKey(token+"_"+menus_token);
        List<SysMenus> list=new ArrayList<>();
        if(!StringUtils.isEmpty(meuns)){
            list=JSONArray.parseArray(meuns,SysMenus.class);
        }
       return list;
    }

    /**
     * 删除菜单
     * @param token
     */
    public void removeMenus(String token){
        redisUtilsService.deleteKey(token+"_"+menus_token);
    }
}
