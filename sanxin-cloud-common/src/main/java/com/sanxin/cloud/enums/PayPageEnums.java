package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;

/**
 * 支付页面枚举，用于对应查询支付方式
 * @author xiaoky
 * @date 2019-10-16
 */
public enum PayPageEnums {
    ORDER(1, "订单"),
    RECHARGE_DEPOSIT(2, "充值押金"),
    HOUR_GIFT(3, "购买时长"),
    RECHARGE_MONEY(4, "充值余额");

    private Integer type;
    private String name;

    private PayPageEnums(Integer type, String name) {
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
        for(PayPageEnums e: PayPageEnums.values()) {
            if(id==e.getType()) {
                return e.getName();
            }
        }
        return "";
    }

    /**
     * 校验数据是否符合
     * @param id
     * @return
     */
    public static boolean isPayPage(Integer id) {
        for (PayPageEnums o : PayPageEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getType())) {
                return true;
            }
        }
        return false;
    }
}
