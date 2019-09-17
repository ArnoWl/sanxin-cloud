package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺申请表
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
public class BBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cid;

    /**
     * 店铺编号
     */
    private String code;

    /**
     * 加盟商店铺昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 头像
     */
    private String headUrl;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 支付密码
     */
    private String payWord;

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
     * 护照
     */
    private String passPort;

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
     * 店铺地址的经度
     */
    private String lonVal;
    /**
     * 店铺地址的纬度
     */
    private String latVal;
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
     * 门店banner图
     */
    private String coverUrl;
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
    /**
     * banner图集合
     */
    @TableField(exist = false)
    private List<String> coverUrlList;
    /**
     * 距离
     */
    @TableField(exist = false)
    private Integer distance;

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
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

    public String getPassPort() {
        return passPort;
    }

    public void setPassPort(String passPort) {
        this.passPort = passPort;
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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getCoverUrlList() {
        return coverUrlList;
    }

    public void setCoverUrlList(List<String> coverUrlList) {
        this.coverUrlList = coverUrlList;
    }

    public String getPayWord() {
        return payWord;
    }

    public void setPayWord(String payWord) {
        this.payWord = payWord;
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
