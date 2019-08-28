package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
public class BBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 登录密码
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
     * 证件类型
     */
    private Integer cardType;

    /**
     * 证件号码
     */
    private String cardNo;

    /**
     * 证件正面
     */
    private String cardFront;

    /**
     * 证件反面
     */
    private String cardBack;

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
    /**
     * 证件类型
     */
    @TableField(exist = false)
    private String cardTypeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }
    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
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

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    @Override
    public String toString() {
        return "BBusiness{" +
        "id=" + id +
        ", nickName=" + nickName +
        ", phone=" + phone +
        ", countryId=" + countryId +
        ", proId=" + proId +
        ", cityId=" + cityId +
        ", areaId=" + areaId +
        ", addressDetail=" + addressDetail +
        ", cardType=" + cardType +
        ", cardNo=" + cardNo +
        ", cardFront=" + cardFront +
        ", cardBack=" + cardBack +
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
