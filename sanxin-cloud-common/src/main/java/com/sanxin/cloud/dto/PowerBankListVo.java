package com.sanxin.cloud.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PowerBankListVo {
    /**
     * Id
     */
    private Integer id;
    /**
     * 设备编号
     */
    private String code;
    /**
     * 商铺名称
     */
    private String companyName;
    /**
     * 头像地址
     */
    private String headUrl;
    /**
     * 营业时间——开始日期(星期)
     */
    private Integer startDay;
    /**
     * 营业时间——结束日期(星期)
     */
    private Integer endDay;
    /**
     * 营业时间——开始时间(小时)
     */
    private String startTime;
    /**
     * 营业时间——结束时间(小时)
     */
    private String endTime;
    /**
     * 可以借出的口(充电宝)
     */
    private Integer lendPort;
    /**
     * 可以归还的口(充电宝)
     */
    private Integer repayPort;
    /**
     *可以借出的口(提示)
     */
    private String strLendPort;
    /**
     * 可以归还的口(提示)
     */
    private String strRepayPort;
    /**
     * 大机柜 小机柜
     */
    @TableField(exist = false)
    private String cabinet;
    /**
     * 详细地址
     */
    private String addressDetail;
    /**
     * 距离
     */
    private Integer distance;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态 1 连接中  2 连接成功(运行中)  3 关闭(暂停)
     */
    private Integer status;
    /**
     * banner图
     */
    private String coverUrl;

    private String businessHours;

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getLendPort() {
        return lendPort;
    }

    public void setLendPort(Integer lendPort) {
        this.lendPort = lendPort;
    }

    public Integer getRepayPort() {
        return repayPort;
    }

    public void setRepayPort(Integer repayPort) {
        this.repayPort = repayPort;
    }

    public String getStrLendPort() {
        return strLendPort;
    }

    public void setStrLendPort(String strLendPort) {
        this.strLendPort = strLendPort;
    }

    public String getStrRepayPort() {
        return strRepayPort;
    }

    public void setStrRepayPort(String strRepayPort) {
        this.strRepayPort = strRepayPort;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public String toString() {
        return "PowerBankListVo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", companyName='" + companyName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", startDay=" + startDay +
                ", endDay=" + endDay +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", lendPort=" + lendPort +
                ", repayPort=" + repayPort +
                ", strLendPort='" + strLendPort + '\'' +
                ", strRepayPort='" + strRepayPort + '\'' +
                ", cabinet='" + cabinet + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", distance=" + distance +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", coverUrl='" + coverUrl + '\'' +
                ", businessHours='" + businessHours + '\'' +
                '}';
    }
}
