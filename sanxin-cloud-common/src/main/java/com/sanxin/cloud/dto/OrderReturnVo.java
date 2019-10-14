package com.sanxin.cloud.dto;

import java.math.BigDecimal;

/**
 * 归还充电宝返回数据
 * @author xiaoky
 * @date 2019-10-11
 */
public class OrderReturnVo {
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 订单状态 见OrderStatusEnums
     */
    private Integer orderStatus;
    /**
     * 使用时长
     */
    private String useHour;
    /**
     * 租金总额
     */
    private BigDecimal rentMoney;
    /**
     * 扣除时长
     */
    private Integer hour;
    /**
     * 扣除余额
     */
    private BigDecimal money;

    /**
     * Gets the value of orderCode.
     *
     * @return the value of orderCode
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * Sets the orderCode.
     *
     * <p>You can use getOrderCode() to get the value of orderCode</p>
     *
     * @param orderCode orderCode
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * Gets the value of orderStatus.
     *
     * @return the value of orderStatus
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the orderStatus.
     *
     * <p>You can use getOrderStatus() to get the value of orderStatus</p>
     *
     * @param orderStatus orderStatus
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Gets the value of useHour.
     *
     * @return the value of useHour
     */
    public String getUseHour() {
        return useHour;
    }

    /**
     * Sets the useHour.
     *
     * <p>You can use getUseHour() to get the value of useHour</p>
     *
     * @param useHour useHour
     */
    public void setUseHour(String useHour) {
        this.useHour = useHour;
    }

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
    public Integer getHour() {
        return hour;
    }

    /**
     * Sets the hour.
     *
     * <p>You can use getHour() to get the value of hour</p>
     *
     * @param hour hour
     */
    public void setHour(Integer hour) {
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
}
