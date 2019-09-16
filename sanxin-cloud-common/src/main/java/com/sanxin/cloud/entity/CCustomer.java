package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
@Data
public class CCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 手机号/账号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 交易密码
     */
    private String payWord;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headUrl;

    /**
     * 国家区号
     */
    private String areaCode;

    /**
     * 国家编号
     */
    private String countryCode;

    /**
     * 是否实名 0未实名 1实名
     */
    private Integer isReal;

    /**
     * 1有效 0无效
     */
    private Integer status;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     *
     */
    @TableField(exist = false)
    private CAccount account;

    /**
     * 验证码
     */
    @TableField(exist = false)
    private String verCode;

    /**
     * token
     */
    @TableField(exist = false)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
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
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getPayWord() {
        return payWord;
    }

    public void setPayWord(String payWord) {
        this.payWord = payWord;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public Integer getIsReal() {
        return isReal;
    }

    public void setIsReal(Integer isReal) {
        this.isReal = isReal;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CAccount getAccount() {
        return account;
    }

    public void setAccount(CAccount account) {
        this.account = account;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    @Override
    public String toString() {
        return "CCustomer{" +
                "id=" + id +
                ", inviteCode='" + inviteCode + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", passWord='" + passWord + '\'' +
                ", payWord='" + payWord + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", isReal=" + isReal +
                ", status=" + status +
                ", createTime=" + createTime +
                ", account=" + account +
                ", verCode=" + verCode +
                '}';
    }
}
