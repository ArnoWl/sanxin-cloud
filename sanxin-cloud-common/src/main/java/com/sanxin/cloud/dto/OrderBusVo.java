package com.sanxin.cloud.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 加盟商
 * @author xiaoky
 * @date 2019-09-21
 */
public class OrderBusVo {
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 充电宝id
     */
    private String terminalId;
    /**
     * 订单状态 见OrderStatusEnums
     */
    private Integer orderStatus;
    /**
     * 订单状态名称
     */
    private String statusName;
    /**
     * 租借人
     */
    private String cusName;
    /**
     * 使用时长
     */
    private String useHour;
    /**
     * 预计租金
     */
    private BigDecimal estimatedRentMoney;

    /**
     * 是否购买充电宝 1购买了 0未购买(正常借还)
     */
    private Integer buy;

    /**
     * 购买充电宝价格
     */
    private BigDecimal terminalMoney;
    /**
     * 购买充电宝扣除押金金额
     */
    private BigDecimal depositMoney;
    /**
     * 租借时间
     */
    private Date rentTime;
    /**
     * 租借地点
     */
    private String addressDetail;


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
     * Gets the value of terminalId.
     *
     * @return the value of terminalId
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * Sets the terminalId.
     *
     * <p>You can use getTerminalId() to get the value of terminalId</p>
     *
     * @param terminalId terminalId
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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
     * Gets the value of statusName.
     *
     * @return the value of statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Sets the statusName.
     *
     * <p>You can use getStatusName() to get the value of statusName</p>
     *
     * @param statusName statusName
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Gets the value of cusName.
     *
     * @return the value of cusName
     */
    public String getCusName() {
        return cusName;
    }

    /**
     * Sets the cusName.
     *
     * <p>You can use getCusName() to get the value of cusName</p>
     *
     * @param cusName cusName
     */
    public void setCusName(String cusName) {
        this.cusName = cusName;
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
     * Gets the value of estimatedRentMoney.
     *
     * @return the value of estimatedRentMoney
     */
    public BigDecimal getEstimatedRentMoney() {
        return estimatedRentMoney;
    }

    /**
     * Sets the estimatedRentMoney.
     *
     * <p>You can use getEstimatedRentMoney() to get the value of estimatedRentMoney</p>
     *
     * @param estimatedRentMoney estimatedRentMoney
     */
    public void setEstimatedRentMoney(BigDecimal estimatedRentMoney) {
        this.estimatedRentMoney = estimatedRentMoney;
    }

    /**
     * Gets the value of buy.
     *
     * @return the value of buy
     */
    public Integer getBuy() {
        return buy;
    }

    /**
     * Sets the buy.
     *
     * <p>You can use getBuy() to get the value of buy</p>
     *
     * @param buy buy
     */
    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    /**
     * Gets the value of terminalMoney.
     *
     * @return the value of terminalMoney
     */
    public BigDecimal getTerminalMoney() {
        return terminalMoney;
    }

    /**
     * Sets the terminalMoney.
     *
     * <p>You can use getTerminalMoney() to get the value of terminalMoney</p>
     *
     * @param terminalMoney terminalMoney
     */
    public void setTerminalMoney(BigDecimal terminalMoney) {
        this.terminalMoney = terminalMoney;
    }

    /**
     * Gets the value of depositMoney.
     *
     * @return the value of depositMoney
     */
    public BigDecimal getDepositMoney() {
        return depositMoney;
    }

    /**
     * Sets the depositMoney.
     *
     * <p>You can use getDepositMoney() to get the value of depositMoney</p>
     *
     * @param depositMoney depositMoney
     */
    public void setDepositMoney(BigDecimal depositMoney) {
        this.depositMoney = depositMoney;
    }

    /**
     * Gets the value of rentTime.
     *
     * @return the value of rentTime
     */
    public Date getRentTime() {
        return rentTime;
    }

    /**
     * Sets the rentTime.
     *
     * <p>You can use getRentTime() to get the value of rentTime</p>
     *
     * @param rentTime rentTime
     */
    public void setRentTime(Date rentTime) {
        this.rentTime = rentTime;
    }

    /**
     * Gets the value of addressDetail.
     *
     * @return the value of addressDetail
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * Sets the addressDetail.
     *
     * <p>You can use getAddressDetail() to get the value of addressDetail</p>
     *
     * @param addressDetail addressDetail
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
