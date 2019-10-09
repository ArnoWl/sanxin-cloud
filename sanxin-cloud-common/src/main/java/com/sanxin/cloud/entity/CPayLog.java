package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 支付记录表
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-09
 */
public class CPayLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer cid;

    /**
     * 交易单
     */
    private String payCode;

    /**
     * 第三方交易单号
     */
    private String transCode;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 0未支付 1已支付
     */
    private Integer status;

    /**
     * 业务类型见枚举ServiceEnums
     */
    private Integer serviceType;

    /**
     * 微信第三方信息
     */
    private String userId;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付渠道
     */
    private Integer payChannel;

    private Date createTime;

    /**
     * 附带参数
     */
    private String params;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 类型
     */
    @TableField(exist = false)
    private Integer handleType;

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
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    @Override
    public String toString() {
        return "CPayLog{" +
        "id=" + id +
        ", cid=" + cid +
        ", payCode=" + payCode +
        ", transCode=" + transCode +
        ", payMoney=" + payMoney +
        ", status=" + status +
        ", serviceType=" + serviceType +
        ", userId=" + userId +
        ", payType=" + payType +
        ", payTime=" + payTime +
        ", payChannel=" + payChannel +
        ", createTime=" + createTime +
        ", params=" + params +
        ", version=" + version +
        "}";
    }
}
