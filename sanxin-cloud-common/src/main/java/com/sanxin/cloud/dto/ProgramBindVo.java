package com.sanxin.cloud.dto;

/**
 * 小程序绑定手机号数据封装
 * @author xiaoky
 * @date 2019-11-02
 */
public class ProgramBindVo {
    private String phone;
    private String userId;
    private String verCode;
    private String areaCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
