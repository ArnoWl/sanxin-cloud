package com.sanxin.cloud.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情封装类
 * @author xiaoky
 * @date 2019-09-23
 */
public class OrderBusDetailVo extends OrderBusVo{

    /**
     * 租借总额
     */
    private BigDecimal rentMoney;
    /**
     * 租借时长
     */
    private BigDecimal hour;
    /**
     * 扣除余额
     */
    private BigDecimal money;
    /**
     * 归还时间
     */
    private Date returnTime;
    /**
     * 支付方式
     */
    private Integer payType;
    /**
     * 支付方式名称
     */
    private String payTypeName;
    /**
     * 实际支付
     */
    private BigDecimal realMoney;
    /**
     * 租借地点
     */
    private String rentAddr;
    /**
     * 归还地点
     */
    private String returnAddr;

    /**
     * Gets the value of rentMoney.
     *
     * @return the value of rentMoney
     */
    public BigDecimal getRentMoney() {
        return rentMoney;
    }

    /**
     * Sets the rentMoney.
     *
     * <p>You can use getRentMoney() to get the value of rentMoney</p>
     *
     * @param rentMoney rentMoney
     */
    public void setRentMoney(BigDecimal rentMoney) {
        this.rentMoney = rentMoney;
    }

    /**
     * Gets the value of hour.
     *
     * @return the value of hour
     */
    public BigDecimal getHour() {
        return hour;
    }

    /**
     * Sets the hour.
     *
     * <p>You can use getHour() to get the value of hour</p>
     *
     * @param hour hour
     */
    public void setHour(BigDecimal hour) {
        this.hour = hour;
    }

    /**
     * Gets the value of money.
     *
     * @return the value of money
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Sets the money.
     *
     * <p>You can use getMoney() to get the value of money</p>
     *
     * @param money money
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * Gets the value of returnTime.
     *
     * @return the value of returnTime
     */
    public Date getReturnTime() {
        return returnTime;
    }

    /**
     * Sets the returnTime.
     *
     * <p>You can use getReturnTime() to get the value of returnTime</p>
     *
     * @param returnTime returnTime
     */
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * Gets the value of payType.
     *
     * @return the value of payType
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * Sets the payType.
     *
     * <p>You can use getPayType() to get the value of payType</p>
     *
     * @param payType payType
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * Gets the value of payTypeName.
     *
     * @return the value of payTypeName
     */
    public String getPayTypeName() {
        return payTypeName;
    }

    /**
     * Sets the payTypeName.
     *
     * <p>You can use getPayTypeName() to get the value of payTypeName</p>
     *
     * @param payTypeName payTypeName
     */
    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    /**
     * Gets the value of realMoney.
     *
     * @return the value of realMoney
     */
    public BigDecimal getRealMoney() {
        return realMoney;
    }

    /**
     * Sets the realMoney.
     *
     * <p>You can use getRealMoney() to get the value of realMoney</p>
     *
     * @param realMoney realMoney
     */
    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }

    /**
     * Gets the value of rentAddr.
     *
     * @return the value of rentAddr
     */
    public String getRentAddr() {
        return rentAddr;
    }

    /**
     * Sets the rentAddr.
     *
     * <p>You can use getRentAddr() to get the value of rentAddr</p>
     *
     * @param rentAddr rentAddr
     */
    public void setRentAddr(String rentAddr) {
        this.rentAddr = rentAddr;
    }

    /**
     * Gets the value of returnAddr.
     *
     * @return the value of returnAddr
     */
    public String getReturnAddr() {
        return returnAddr;
    }

    /**
     * Sets the returnAddr.
     *
     * <p>You can use getReturnAddr() to get the value of returnAddr</p>
     *
     * @param returnAddr returnAddr
     */
    public void setReturnAddr(String returnAddr) {
        this.returnAddr = returnAddr;
    }
}
