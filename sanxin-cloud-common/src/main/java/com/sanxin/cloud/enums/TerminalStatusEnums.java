package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

/**
 * 充电宝状态枚举类
 * @author xiaoky
 * @date 2019-09-20
 */
public enum TerminalStatusEnums {
    CHARGING(1, "terminal_status_charging"),
    LENT(2, "terminal_status_lent"),
    SOLD(3, "terminal_status_sold");

    private Integer status;
    private String name;

    private TerminalStatusEnums(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    /**
     * Gets the value of status.
     *
     * @return the value of status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * <p>You can use getStatus() to get the value of status</p>
     *
     * @param status status
     */
    public void setStatus(Integer status) {
        this.status = status;
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

    public static String getName(Integer status) {
        String str = "";
        for (TerminalStatusEnums o : TerminalStatusEnums.values()) {
            if (FunctionUtils.isEquals(status, o.getStatus())) {
                str = LanguageUtils.getMessage(o.getName());
                break;
            }
        }
        return str;
    }

    public static Boolean validStatus(Integer status) {
        for (TerminalStatusEnums o : TerminalStatusEnums.values()) {
            if (FunctionUtils.isEquals(status, o.getStatus())) {
                return true;
            }
        }
        return false;
    }
}
