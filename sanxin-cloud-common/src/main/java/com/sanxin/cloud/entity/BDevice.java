package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 店铺设备
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-06
 */
public class BDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应店铺id
     */
    private Integer bid;

    /**
     * 设备编号
     */
    private String code;

    /**
     * 设备类型  见DeviceEnums
     */
    private Integer type;
    /**
     * 状态 1 连接中  2 连接成功(运行中)  3 关闭(暂停)
     */
    private Integer status;
    /**
     * 该设备一共多少个口(充电宝)
     */
    private Integer allPort;
    /**
     * 可以借出的口(充电宝)
     */
    private Integer lendPort;
    /**
     * 可以归还的口(充电宝)
     */
    private Integer repayPort;
    /**
     * 最后开启时间
     */
    private Date lastOpenTime;
    /**
     * 最后关闭时间
     */
    private Date lastCloseTime;
    /**
     * 设备添加时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    /**
     * 对应店铺名称
     */
    @TableField(exist = false)
    private String businessName;
    /**
     * 状态名称
     */
    @TableField(exist = false)
    private String statusName;

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
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAllPort() {
        if (allPort == null) {
            return 0;
        }
        return allPort;
    }

    public void setAllPort(Integer allPort) {
        this.allPort = allPort;
    }

    public Integer getLendPort() {
        if (lendPort == null) {
            return 0;
        }
        return lendPort;
    }

    public void setLendPort(Integer lendPort) {
        this.lendPort = lendPort;
    }

    public Integer getRepayPort() {
        if (repayPort == null) {
            return 0;
        }
        return repayPort;
    }

    public void setRepayPort(Integer repayPort) {
        this.repayPort = repayPort;
    }

    public Date getLastOpenTime() {
        return lastOpenTime;
    }

    public void setLastOpenTime(Date lastOpenTime) {
        this.lastOpenTime = lastOpenTime;
    }

    public Date getLastCloseTime() {
        return lastCloseTime;
    }

    public void setLastCloseTime(Date lastCloseTime) {
        this.lastCloseTime = lastCloseTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "BDevice{" +
        "id=" + id +
        ", bid=" + bid +
        ", code=" + code +
        ", type=" + type +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
