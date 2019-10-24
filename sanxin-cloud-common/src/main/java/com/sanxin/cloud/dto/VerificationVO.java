package com.sanxin.cloud.dto;

import lombok.Data;

@Data
public class VerificationVO {
    /**
     * 第三方token
     */
    private String accessToken;
    /**
     * 第三方id
     */
    private String id;
    /**
     * 第三方name
     */
    private String name;
    /**
     * 1facebook 2google
     */
    private String type;
    /**
     * 用户密码
     */
    private String passWord;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String verCode;
    /**
     * 区号
     */
    private String areaCode;
    /**
     * 第三方图片
     */
    private String picture;
    /**
     * 第三方邮箱
     */
    private String email;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "VerificationVO{" +
                "accessToken='" + accessToken + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", passWord='" + passWord + '\'' +
                ", phone='" + phone + '\'' +
                ", verCode='" + verCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
