package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单记录表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-21
 */
@Data
public class OrderMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer cid;

    /**
     * 店铺id
     */
    private Integer bid;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 支付单号
     */
    private String payCode;

    /**
     * 第三方交易单号
     */
    private String transCode;

    /**
     * 充电宝id
     */
    private String terminalId;

    /**
     * 租金总额
     */
    private BigDecimal rentMoney;

    /**
     * 扣除时长
     */
    private BigDecimal hour;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 实际支付
     */
    private BigDecimal realMoney;

    /**
     * 订单状态 见OrderStatusEnums
     */
    private Integer orderStatus;

    /**
     * 是否删除 1已删除 0未删除
     */
    private Integer del;
    /**
     * 是否购买充电宝 1购买了 0未购买(正常借还)
     */
    private Integer buy;

    /**
     * 支付方式 见枚举PaytypeEnums
     */
    private Integer payType;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 确认去支付时间
     */
    private Date confirmTime;

    /**
     * 支付截止时间
     */
    private Date payendTime;

    /**
     * 支付完成时间
     */
    private Date payTime;

    /**
     * 归还时间
     */
    private Date returnTime;

    /**
     * 订单来源渠道 见枚举LoginChannelEnums
     */
    private Integer fromChannel;
    /**
     * 购买充电宝价格
     */
    private BigDecimal terminalMoney;
    /**
     * 购买充电宝扣除押金金额
     */
    private BigDecimal depositMoney;
    /**
     * 租借地点
     */
    private String rentAddr;
    /**
     * 归还地点
     */
    private String returnAddr;

    /**
     * 交易完成时间
     */
    private Date overTime;
    /**
     * 订单状态名称
     */
    @TableField(exist = false)
    private String statusName;
    /**
     * 查询数据
     */
    @TableField(exist = false)
    private String key;

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
    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
    public BigDecimal getRentMoney() {
        return rentMoney;
    }

    public void setRentMoney(BigDecimal rentMoney) {
        this.rentMoney = rentMoney;
    }
    public BigDecimal getHour() {
        return hour;
    }

    public void setHour(BigDecimal hour) {
        this.hour = hour;
    }
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }
    public BigDecimal getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }
    public Date getPayendTime() {
        return payendTime;
    }

    public void setPayendTime(Date payendTime) {
        this.payendTime = payendTime;
    }
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getFromChannel() {
        return fromChannel;
    }

    public void setFromChannel(Integer fromChannel) {
        this.fromChannel = fromChannel;
    }
    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public BigDecimal getTerminalMoney() {
        return terminalMoney;
    }

    public void setTerminalMoney(BigDecimal terminalMoney) {
        this.terminalMoney = terminalMoney;
    }

    public BigDecimal getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(BigDecimal depositMoney) {
        this.depositMoney = depositMoney;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRentAddr() {
        return rentAddr;
    }

    public void setRentAddr(String rentAddr) {
        this.rentAddr = rentAddr;
    }

    public String getReturnAddr() {
        return returnAddr;
    }

    public void setReturnAddr(String returnAddr) {
        this.returnAddr = returnAddr;
    }

    @Override
    public String toString() {
        return "OrderMain{" +
        "id=" + id +
        ", cid=" + cid +
        ", bid=" + bid +
        ", orderCode=" + orderCode +
        ", payCode=" + payCode +
        ", transCode=" + transCode +
        ", terminalId=" + terminalId +
        ", rentMoney=" + rentMoney +
        ", hour=" + hour +
        ", payMoney=" + payMoney +
        ", realMoney=" + realMoney +
        ", orderStatus=" + orderStatus +
        ", del=" + del +
        ", payType=" + payType +
        ", createTime=" + createTime +
        ", confirmTime=" + confirmTime +
        ", payendTime=" + payendTime +
        ", payTime=" + payTime +
        ", returnTime=" + returnTime +
        ", fromChannel=" + fromChannel +
        ", overTime=" + overTime +
        "}";
    }
}
