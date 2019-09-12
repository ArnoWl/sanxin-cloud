package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 余额提现记录表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public class SysCashDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流水号
     */
    private String payCode;

    /**
     * 提现人id
     */
    private Integer targetId;

    /**
     * 提现人真实名称
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCode;

    /**
     * 银行卡绑定手机
     */
    private String phone;

    /**
     * 开户行地址
     */
    private String bankAddr;

    /**
     * 提现金额
     */
    private BigDecimal cashMoney;

    /**
     * 实际应到账金额
     */
    private BigDecimal realMoney;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核完成时间
     */
    private Date endTime;

    /**
     * 1申请审核中  2审核通过 3审核驳回
     */
    private Integer status;

    /**
     * 驳回备注
     */
    private String remark;

    private Integer type;

    /**
     * 对应银行卡列表id
     */
    private Integer bankId;
    /**
     * 如果是加盟商提现——选择的税率
     */
    private BigDecimal tax;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
    }
    public BigDecimal getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(BigDecimal cashMoney) {
        this.cashMoney = cashMoney;
    }
    public BigDecimal getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "SysCashDetail{" +
        "id=" + id +
        ", payCode=" + payCode +
        ", targetId=" + targetId +
        ", realName=" + realName +
        ", idCard=" + idCard +
        ", bankName=" + bankName +
        ", bankCode=" + bankCode +
        ", phone=" + phone +
        ", bankAddr=" + bankAddr +
        ", cashMoney=" + cashMoney +
        ", realMoney=" + realMoney +
        ", createTime=" + createTime +
        ", endTime=" + endTime +
        ", status=" + status +
        ", remark=" + remark +
        ", type=" + type +
        ", bankId=" + bankId +
        "}";
    }
}
