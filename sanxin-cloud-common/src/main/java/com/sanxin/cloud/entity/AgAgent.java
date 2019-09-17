package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 代理
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-29
 */
public class AgAgent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer cid;
    /**
     * 姓名
     */
    private String realName;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 地区-国家
     */
    private Integer countryId;

    /**
     * 地区-省份
     */
    private Integer proId;

    /**
     * 地区-城市
     */
    private Integer cityId;

    /**
     * 地区-区县
     */
    private Integer areaId;

    /**
     * 地区-详细地址
     */
    private String addressDetail;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 营业执照号
     */
    private String licenseCode;

    /**
     * 营业执照图片-上传地址
     */
    private String licenseImg;

    /**
     * 公司照片-上传地址
     */
    private String companyImg;

    /**
     * 申请状态
     */
    private Integer status;

    /**
     * 申请时间
     */
    private Date createTime;

    /**
     * 审核时间
     */
    private Date checkTime;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }
    public String getLicenseImg() {
        return licenseImg;
    }

    public void setLicenseImg(String licenseImg) {
        this.licenseImg = licenseImg;
    }
    public String getCompanyImg() {
        return companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
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
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "AgAgent{" +
        "id=" + id +
        ", realName=" + realName +
        ", phone=" + phone +
        ", countryId=" + countryId +
        ", proId=" + proId +
        ", cityId=" + cityId +
        ", areaId=" + areaId +
        ", addressDetail=" + addressDetail +
        ", companyName=" + companyName +
        ", licenseCode=" + licenseCode +
        ", licenseImg=" + licenseImg +
        ", companyImg=" + companyImg +
        ", status=" + status +
        ", createTime=" + createTime +
        ", checkTime=" + checkTime +
        "}";
    }
}
