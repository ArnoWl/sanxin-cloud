package com.sanxin.cloud.dto;

/**
 * 商家个人资料vo类封装
 * @author xiaoky
 * @date 2019-09-18
 */
public class BusinessBaseVo {

    /**
     * 头像
     */
    private String headUrl;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 证件类型
     */
    private Integer cardType;
    /**
     * 证件类型
     */
    private String cardTypeName;
    /**
     * 证件号码
     */
    private String cardNo;

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
     * Gets the value of phone.
     *
     * @return the value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * <p>You can use getPhone() to get the value of phone</p>
     *
     * @param phone phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the value of cardType.
     *
     * @return the value of cardType
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * Sets the cardType.
     *
     * <p>You can use getCardType() to get the value of cardType</p>
     *
     * @param cardType cardType
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    /**
     * Gets the value of cardTypeName.
     *
     * @return the value of cardTypeName
     */
    public String getCardTypeName() {
        return cardTypeName;
    }

    /**
     * Sets the cardTypeName.
     *
     * <p>You can use getCardTypeName() to get the value of cardTypeName</p>
     *
     * @param cardTypeName cardTypeName
     */
    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    /**
     * Gets the value of cardNo.
     *
     * @return the value of cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Sets the cardNo.
     *
     * <p>You can use getCardNo() to get the value of cardNo</p>
     *
     * @param cardNo cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
