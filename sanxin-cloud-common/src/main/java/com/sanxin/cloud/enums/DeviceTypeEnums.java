package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

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
    LARGE_CABINET(1, "device_type_large"),
    SMALL_CABINET(2, "device_type_small");

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

    public static String getName(Integer id) {
        String str = "";
        for (DeviceTypeEnums o : DeviceTypeEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getType())) {
                str = LanguageUtils.getMessage(o.getName());
                break;
            }
        }
        return str;
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

    public static boolean validType(Integer type) {
        for (DeviceTypeEnums o : DeviceTypeEnums.values()) {
            if (FunctionUtils.isEquals(type, o.getType())) {
                return true;
            }
        }
        return false;
    }
}
