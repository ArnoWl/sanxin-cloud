package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

/**
 * 星期枚举类(用于营业时间星期对应)
 * @author xiaoky
 * @date 2019-09-17
 */
public enum WeekEnums {
    MONDAY(1, "week_monday"),
    TUESDAY(2, "week_tuesday"),
    WEDNESDAY(3, "week_wednesday"),
    THURSDAY(4, "week_thursday"),
    FRIDAY(5, "week_friday"),
    SATURDAY(6, "week_saturday"),
    SUNDAY(7, "week_sunday");

    private Integer id;
    private String name;

    private WeekEnums(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the value of id.
     *
     * @return the value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * <p>You can use getId() to get the value of id</p>
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
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
        for (WeekEnums o : WeekEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getId())) {
                str = LanguageUtils.getMessage(o.getName());
                break;
            }
        }
        return str;
    }
}
