package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备类型枚举类
 * @author xiaoky
 * @date 2019-09-06
 */
public enum DeviceTypeEnums {
    LARGE_CABINET(1, "大柜机"),
    SMALL_CABINET(2, "小柜机");

    private Integer type;
    private String name;

    private DeviceTypeEnums(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Gets the value of type.
     *
     * @return the value of type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * <p>You can use getType() to get the value of type</p>
     *
     * @param type type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets the value of name.
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * <p>You can use getName() to get the value of name</p>
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    public static String getName(int id) {
        for(DeviceTypeEnums e: DeviceTypeEnums.values()) {
            if (FunctionUtils.isEquals(id, e.getType())) {
                return e.getName();
            }
        }
        return "";
    }

    public static List<Map<String, Object>> queryMap(){
        List<Map<String, Object>> list = new ArrayList<>();
        for(DeviceTypeEnums o: DeviceTypeEnums.values()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", o.getName());
            map.put("type", o.getType());
            list.add(map);
        }
        return list;
    }
}
