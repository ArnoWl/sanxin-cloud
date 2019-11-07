package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 加盟商余额明细
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-19
 */
public class BMoneyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer bid;

    /**
     * 关联单号
     */
    private String payCode;

    /**
     * 操作类型
     */
    private Integer typeId;

    /**
     * 1收入 0支出
     */
    private Integer isout;

    /**
     * 上一次金额
     */
    private BigDecimal original;

    /**
     * 本次操作金额
     */
    private BigDecimal cost;

    /**
     * 最后结余
     */
    private BigDecimal last;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * 触发人id 只表示用户id
     */
    private Integer targetId;

    /**
     * 描述
     */
    private String remark;
    /**
     * 查询条件-开始时间
     */
    @TableField(exist = false)
    private String startTime;
    /**
     * 查询条件-结束时间
     */
    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String typeName;

    public BMoneyDetail(){}

    public BMoneyDetail(Integer bid, Integer type, Integer isout, String payCode, BigDecimal cost, String remark) {
        this.bid = bid;
        this.typeId = type;
        this.isout = isout;
        this.payCode = payCode;
        this.cost = cost;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }
    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public Integer getIsout() {
        return isout;
    }

    public void setIsout(Integer isout) {
        this.isout = isout;
    }
    public BigDecimal getOriginal() {
        return original;
    }

    public void setOriginal(BigDecimal original) {
        this.original = original;
    }
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "BMoneyDetail{" +
        "id=" + id +
        ", bid=" + bid +
        ", payCode=" + payCode +
        ", typeId=" + typeId +
        ", isout=" + isout +
        ", original=" + original +
        ", cost=" + cost +
        ", last=" + last +
        ", createTime=" + createTime +
        ", targetId=" + targetId +
        ", remark=" + remark +
        "}";
    }
}
