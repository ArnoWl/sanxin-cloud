package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 押金明细
 * </p>
 *
 * @author Arno
 * @since 2019-09-19
 */
public class CMarginDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer cid;

    /**
     * 关联单号
     */
    private String payCode;

    /**
     * 1充值 0提现
     */
    private Integer isout;

    /**
     * 支付方式
     */
    private Integer type;

    /**
     * 操作金额
     */
    private BigDecimal cost;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * 描述
     */
    private String remark;

    /**
     * 昵称
     */
    @TableField(exist = false)
    private String nickName;

    public CMarginDetail(){}

    public CMarginDetail(Integer cid, Integer type, Integer isout, String payCode, BigDecimal cost, String remark) {
        this.cid = cid;
        this.type = type;
        this.isout = isout;
        this.payCode = payCode;
        this.cost = cost;
        this.remark = remark;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public Integer getIsout() {
        return isout;
    }

    public void setIsout(Integer isout) {
        this.isout = isout;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CMarginDetail{" +
                "id=" + id +
                ", cid=" + cid +
                ", payCode=" + payCode +
                ", isout=" + isout +
                ", type=" + type +
                ", cost=" + cost +
                ", createTime=" + createTime +
                ", remark=" + remark +
                "}";
    }
}
