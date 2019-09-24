package com.sanxin.cloud.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CustomerHomeVo {
    private String phone;
    private String email;
    private Integer status;
    private Integer isValid;
    private Date createTime;
    private String token;
    private String headUrl;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public String toString() {
        return "CustomerHomeVo{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", isValid=" + isValid +
                ", createTime=" + createTime +
                ", token='" + token + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }
}
