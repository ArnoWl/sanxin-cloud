package com.sanxin.cloud.enums;

/**
 * 证件类型枚举类
 * @author xiaoky
 * @date 2019-08-28
 */
public enum CardTypeEnums {
    ID_CARD(1, "身份证"),
    PASS_PORT(2, "护照");

    private Integer type;
    private String name;

    private CardTypeEnums(Integer type, String name) {
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
        for(CardTypeEnums e:CardTypeEnums.values()) {
            if(id==e.getType()) {
                return e.getName();
            }
        }
        return "";
    }
}
