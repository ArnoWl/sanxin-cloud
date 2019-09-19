package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 加盟商账户表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-19
 */
public class BAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 店铺id
     */
    private Integer bid;

    /**
     * 总收益
     */
    private BigDecimal totalIncome;

    /**
     * 余额
     */
    private BigDecimal money;

    /**
     * 版本号用作于乐观锁
     */
    private Integer version;

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
    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BAccount{" +
        "id=" + id +
        ", bid=" + bid +
        ", totalIncome=" + totalIncome +
        ", money=" + money +
        ", version=" + version +
        "}";
    }
}
