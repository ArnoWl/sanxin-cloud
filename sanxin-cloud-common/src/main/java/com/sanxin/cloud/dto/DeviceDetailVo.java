package com.sanxin.cloud.dto;

/**
 * 柜机详情封装实体类
 * @author xiaoky
 * @date 2019-09-17
 */
public class DeviceDetailVo {
    /**
     * 设备编号
     */
    private String code;

    /**
     * 设备类型名称
     */
    private String typeName;
    /**
     * 状态 1 连接中  2 连接成功(运行中)  3 关闭(暂停)
     */
    private Integer status;
    /**
     * 可以借出的口(充电宝)
     */
    private Integer lendPort;
    /**
     * 可以归还的口(充电宝)
     */
    private Integer repayPort;
    /**
     * 对应店铺名称
     */
    private String businessName;
    /**
     * 营业时间
     */
    private String hours;
    /**
     * 店铺详细地址
     */
    private String addressDetail;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
