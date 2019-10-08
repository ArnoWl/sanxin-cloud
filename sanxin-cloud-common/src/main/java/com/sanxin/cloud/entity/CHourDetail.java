package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;

import java.io.Serializable;

/**
 * <p>
 * 时长明细表
 * </p>
 *
 * @author Arno
 * @since 2019-10-08
 */
public class CHourDetail implements Serializable {

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
     * 1充值 0提现
     */
    private Integer isout;

    /**
     * 上一次金额
     */
    private Integer original;

    /**
     * 本次操作金额
     */
    private Integer cost;

    /**
     * 最后结余
     */
    private Integer last;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 描述
     */
    private String remark;

    public CHourDetail(Integer cid, Integer type, Integer isout, String payCode, Integer cost, String remark) {
        this.cid = cid;
        this.type = type;
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
    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
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
        return "CHourDetail{" +
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
