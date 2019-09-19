package com.sanxin.cloud.dto;

import java.util.List;

/**
 * 商家中心vo类封装
 * @author xiaoky
 * @date 2019-09-18
 */
public class BusinessDetailVo {
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 昵称
     */
    private String nickName;

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
     * 地区-详细地址
     */
    private String addressDetail;
    /**
     * 门店背景图集合
     */
    private List<String> coverUrlList;
    /**
     * 营业时间(星期)
     */
    private String businessDay;
    /**
     * 营业时间(小时)
     */
    private String businessTime;

    /**
     * Gets the value of headUrl.
     *
     * @return the value of headUrl
     */
    public String getHeadUrl() {
        return headUrl;
    }

    /**
     * Sets the headUrl.
     *
     * <p>You can use getHeadUrl() to get the value of headUrl</p>
     *
     * @param headUrl headUrl
     */
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    /**
     * Gets the value of nickName.
     *
     * @return the value of nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets the nickName.
     *
     * <p>You can use getNickName() to get the value of nickName</p>
     *
     * @param nickName nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Gets the value of startDay.
     *
     * @return the value of startDay
     */
    public Integer getStartDay() {
        return startDay;
    }

    /**
     * Sets the startDay.
     *
     * <p>You can use getStartDay() to get the value of startDay</p>
     *
     * @param startDay startDay
     */
    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    /**
     * Gets the value of endDay.
     *
     * @return the value of endDay
     */
    public Integer getEndDay() {
        return endDay;
    }

    /**
     * Sets the endDay.
     *
     * <p>You can use getEndDay() to get the value of endDay</p>
     *
     * @param endDay endDay
     */
    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    /**
     * Gets the value of startTime.
     *
     * @return the value of startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the startTime.
     *
     * <p>You can use getStartTime() to get the value of startTime</p>
     *
     * @param startTime startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the value of endTime.
     *
     * @return the value of endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the endTime.
     *
     * <p>You can use getEndTime() to get the value of endTime</p>
     *
     * @param endTime endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    /**
     * Gets the value of coverUrlList.
     *
     * @return the value of coverUrlList
     */
    public List<String> getCoverUrlList() {
        return coverUrlList;
    }

    /**
     * Sets the coverUrlList.
     *
     * <p>You can use getCoverUrlList() to get the value of coverUrlList</p>
     *
     * @param coverUrlList coverUrlList
     */
    public void setCoverUrlList(List<String> coverUrlList) {
        this.coverUrlList = coverUrlList;
    }

    /**
     * Gets the value of businessDay.
     *
     * @return the value of businessDay
     */
    public String getBusinessDay() {
        return businessDay;
    }

    /**
     * Sets the businessDay.
     *
     * <p>You can use getBusinessDay() to get the value of businessDay</p>
     *
     * @param businessDay businessDay
     */
    public void setBusinessDay(String businessDay) {
        this.businessDay = businessDay;
    }

    /**
     * Gets the value of businessTime.
     *
     * @return the value of businessTime
     */
    public String getBusinessTime() {
        return businessTime;
    }

    /**
     * Sets the businessTime.
     *
     * <p>You can use getBusinessTime() to get the value of businessTime</p>
     *
     * @param businessTime businessTime
     */
    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }
}
