package com.sanxin.cloud.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告跳转
 * 事件类型枚举
 * @author xiaoky
 * @date 2019-09-04
 */
public enum EventEnums {
    HOUR_GIFT("hourGift","时长礼包"),
    RECHARGE("recharge","充值"),
    EXTERNAL_LINK("externalLink","外部url"),
    BUSINESS("business","跳转商家");

    private String url;
    private String name;

    EventEnums(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 查询所有列表
     */
    public static List<EventEnums> queryAllList() {
        List<EventEnums> list = new ArrayList<EventEnums>();
        for (EventEnums o : EventEnums.values()) {
            list.add(o);
        }
        return list;
    }

    public static String getName(String ext) {
        String str = "";
        for (EventEnums o : EventEnums.values()) {
            if (o.getUrl().equals(ext)) {
                str = o.getName();
                break;
            }
        }
        return str;
    }

    public static EventEnums getObject(String event) {
        EventEnums enums = null;
        for (EventEnums o : EventEnums.values()) {
            if (o.getUrl().equals(event)) {
                enums = o;
                break;
            }
        }
        return enums;
    }

    public static List<Map<String, Object>> queryMap(){
        List<Map<String, Object>> list = new ArrayList<>();
        for(EventEnums o:EventEnums.values()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", o.getName());
            map.put("url", o.getUrl());
            list.add(map);
        }
        return list;
    }
}
