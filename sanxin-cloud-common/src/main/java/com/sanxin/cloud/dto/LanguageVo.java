package com.sanxin.cloud.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 语言数据实体类
 * @author xiaoky
 * @date 2019-09-04
 */
public class LanguageVo {

    /**
     * 中文
     */
    private String CN;
    /**
     * 英文
     */
    private String EN;
    /**
     * 泰文
     */
    private String THAI;

    public LanguageVo(String CN, String EN, String THAI) {
        this.CN = CN;
        this.EN = EN;
        this.THAI = THAI;
    }

    /**
     * Gets the value of CN.
     *
     * @return the value of CN
     */
    @JSONField(name = "CN")
    public String getCN() {
        return CN;
    }

    /**
     * Sets the CN.
     *
     * <p>You can use getCN() to get the value of CN</p>
     *
     * @param CN CN
     */
    public void setCN(String CN) {
        this.CN = CN;
    }

    /**
     * Gets the value of EN.
     *
     * @return the value of EN
     */
    @JSONField(name = "EN")
    public String getEN() {
        return EN;
    }

    /**
     * Sets the EN.
     *
     * <p>You can use getEN() to get the value of EN</p>
     *
     * @param EN EN
     */
    public void setEN(String EN) {
        this.EN = EN;
    }

    /**
     * Gets the value of THAI.
     *
     * @return the value of THAI
     */
    @JSONField(name = "THAI")
    public String getTHAI() {
        return THAI;
    }

    /**
     * Sets the THAI.
     *
     * <p>You can use getTHAI() to get the value of THAI</p>
     *
     * @param THAI THAI
     */
    public void setTHAI(String THAI) {
        this.THAI = THAI;
    }
}
