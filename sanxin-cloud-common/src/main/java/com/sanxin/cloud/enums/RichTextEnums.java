package com.sanxin.cloud.enums;

/**
 * 富文本类型枚举类
 * @author xiaoky
 * @date 2019-09-02
 */
public enum RichTextEnums {
    USE_BATTERY(1, "使用充电宝"),
    RETURN_BATTERY(2, "归还充电宝"),
    ABOUT_US(3, "关于我们");

    private Integer type;
    private String name;

    private RichTextEnums(Integer type, String name) {
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
        for(RichTextEnums e: RichTextEnums.values()) {
            if(id==e.getType()) {
                return e.getName();
            }
        }
        return "";
    }
}
