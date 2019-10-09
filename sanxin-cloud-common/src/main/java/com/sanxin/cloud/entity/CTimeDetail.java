package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户时长明细表
 * </p>
 *
 * @author Arno
 * @since 2019-09-27
 */
public class CTimeDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 操作类型
     */
    private Integer type;

    /**
     * 1充值 0扣除
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

    /**
     * 创建时间
     */
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
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CTimeDetail{" +
        "id=" + id +
        ", cid=" + cid +
        ", payCode=" + payCode +
        ", type=" + type +
        ", isout=" + isout +
        ", original=" + original +
        ", cost=" + cost +
        ", last=" + last +
        ", createTime=" + createTime +
        ", remark=" + remark +
        "}";
    }
}
