package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * <p>
 * 用户账户
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-30
 */
@Data
public class CAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户主键
     */
    private Integer cid;

    /**
     * 余额
     */
    private BigDecimal money;

    /**
     * 时长
     */
    private BigDecimal hour;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 版本号用作于乐观锁
     */
    private Integer version;

    /**
     * 0输入密码 1免输密码
     */
    private Integer password;

    @TableField(exist = false)
    private Integer card;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getHour() {
        return hour;
    }

    public void setHour(BigDecimal hour) {
        this.hour = hour;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "CAccount{" +
                "id=" + id +
                ", cid=" + cid +
                ", money=" + money +
                ", hour=" + hour +
                ", deposit=" + deposit +
                ", version=" + version +
                ", password=" + password +
                ", card=" + card +
                '}';
    }
}
