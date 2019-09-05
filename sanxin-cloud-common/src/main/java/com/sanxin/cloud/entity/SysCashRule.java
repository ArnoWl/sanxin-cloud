package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public class SysCashRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 每笔提现最小金额
     */
    private BigDecimal minVal;

    /**
     * 每笔提现最大金额
     */
    private BigDecimal maxVal;

    /**
     * 提现手续费
     */
    private BigDecimal scale;

    /**
     * 提现次数
     */
    private Integer num;

    /**
     * 0不限制  1限制每日提现次数  2限制每周提现次数 3限制每月提现次数
     */
    private Integer type;

    /**
     * 1开启提现 0关闭提现
     */
    private Integer isOpen;

    /**
     * 提现金额的整数倍
     */
    private Integer multiple;

    /**
     * 1.用户提现 2.商家提现
     */
    private Integer roleType;
    /**
     * 提现税费
     */
    private BigDecimal taxOne;
    /**
     * 提现税费
     */
    private BigDecimal taxTwo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public BigDecimal getMinVal() {
        return minVal;
    }

    public void setMinVal(BigDecimal minVal) {
        this.minVal = minVal;
    }
    public BigDecimal getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(BigDecimal maxVal) {
        this.maxVal = maxVal;
    }
    public BigDecimal getScale() {
        return scale;
    }

    public void setScale(BigDecimal scale) {
        this.scale = scale;
    }
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public BigDecimal getTaxOne() {
        return taxOne;
    }

    public void setTaxOne(BigDecimal taxOne) {
        this.taxOne = taxOne;
    }

    public BigDecimal getTaxTwo() {
        return taxTwo;
    }

    public void setTaxTwo(BigDecimal taxTwo) {
        this.taxTwo = taxTwo;
    }

    @Override
    public String toString() {
        return "SysCashRule{" +
        "id=" + id +
        ", minVal=" + minVal +
        ", maxVal=" + maxVal +
        ", scale=" + scale +
        ", num=" + num +
        ", type=" + type +
        ", isOpen=" + isOpen +
        ", multiple=" + multiple +
        ", roleType=" + roleType +
        "}";
    }
}
