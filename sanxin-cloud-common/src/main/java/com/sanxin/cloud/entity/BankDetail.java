package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 银行卡信息表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public class BankDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 银行卡所属(用户、店铺)
     */
    private Integer targetId;

    /**
     * 银行卡类型(开户行)  对应表 bank_type
     */
    private Integer typeId;

    /**
     * 银行卡归属类型(用户、店铺)
     */
    private Integer type;

    /**
     * 真实姓名(持卡人姓名)
     */
    private String realName;

    /**
     * 证件类型见 CardTypeEnums
     */
    private Integer cardType;

    /**
     * 证件号
     */
    private String cardNo;

    /**
     * 银行卡卡号
     */
    private String bankCard;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 开户行地址
     */
    private String bankAddr;

    /**
     * 预留手机号
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 开户行logo
     */
    private String logo;

    /**
     * 是否泰国卡  0 不是  1 是
     */
    private Integer thaiCard;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
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
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public Integer getThaiCard() {
        return thaiCard;
    }

    public void setThaiCard(Integer thaiCard) {
        this.thaiCard = thaiCard;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BankDetail{" +
        "id=" + id +
        ", targetId=" + targetId +
        ", typeId=" + typeId +
        ", type=" + type +
        ", realName=" + realName +
        ", cardType=" + cardType +
        ", cardNo=" + cardNo +
        ", bankCard=" + bankCard +
        ", bankName=" + bankName +
        ", bankAddr=" + bankAddr +
        ", phone=" + phone +
        ", email=" + email +
        ", logo=" + logo +
        ", thaiCard=" + thaiCard +
        ", createTime=" + createTime +
        "}";
    }
}
