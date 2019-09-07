package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 投放地址
     */
    private String addressDetail;

    /**
     * 经度
     */
    private String lonVal;

    /**
     * 纬度
     */
    private String latVal;

    /**
     * 营业时间-开始(星期)
     */
    private Integer startDay;
    /**
     * 营业时间-结束(星期)
     */
    private Integer endDay;

    /**
     * 营业时间-小时
     */
    private String hourTime;

    /**
     * 状态 0 暂停  1 设备运行中
     */
    private Integer status;

    /**
     * 设备添加时间
     */
    private Date createTime;
    /**
     * 对应店铺名称
     */
    @TableField(exist = false)
    private String businessName;

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
    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
    public String getLonVal() {
        return lonVal;
    }

    public void setLonVal(String lonVal) {
        this.lonVal = lonVal;
    }
    public String getLatVal() {
        return latVal;
    }

    public void setLatVal(String latVal) {
        this.latVal = latVal;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public String getHourTime() {
        return hourTime;
    }

    public void setHourTime(String hourTime) {
        this.hourTime = hourTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "BDevice{" +
        "id=" + id +
        ", bid=" + bid +
        ", code=" + code +
        ", type=" + type +
        ", addressDetail=" + addressDetail +
        ", lonVal=" + lonVal +
        ", latVal=" + latVal +
        ", startDay=" + startDay +
        ", endDay=" + endDay +
        ", hourTime=" + hourTime +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
