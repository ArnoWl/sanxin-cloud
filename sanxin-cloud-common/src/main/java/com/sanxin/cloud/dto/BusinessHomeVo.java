package com.sanxin.cloud.dto;

import java.math.BigDecimal;

/**
 * 加盟商首页数据vo
 * @author xiaoky
 * @date 2019-09-19
 */
public class BusinessHomeVo {
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 是否有未读消息提示
     */
    private Boolean tips;
    /**
     * 余额
     */
    private BigDecimal money;
    /**
     * 今日收益
     */
    private BigDecimal todayIncome;
    /**
     * 本周收益
     */
    private BigDecimal weekIncome;
    /**
     * 本月收益
     */
    private BigDecimal monthIncome;
    /**
     * 总收益
     */
    private BigDecimal totalIncome;

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
     * Gets the value of tips.
     *
     * @return the value of tips
     */
    public Boolean getTips() {
        return tips;
    }

    /**
     * Sets the tips.
     *
     * <p>You can use getTips() to get the value of tips</p>
     *
     * @param tips tips
     */
    public void setTips(Boolean tips) {
        this.tips = tips;
    }

    /**
     * Gets the value of money.
     *
     * @return the value of money
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Sets the money.
     *
     * <p>You can use getMoney() to get the value of money</p>
     *
     * @param money money
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * Gets the value of todayIncome.
     *
     * @return the value of todayIncome
     */
    public BigDecimal getTodayIncome() {
        return todayIncome;
    }

    /**
     * Sets the todayIncome.
     *
     * <p>You can use getTodayIncome() to get the value of todayIncome</p>
     *
     * @param todayIncome todayIncome
     */
    public void setTodayIncome(BigDecimal todayIncome) {
        this.todayIncome = todayIncome;
    }

    /**
     * Gets the value of weekIncome.
     *
     * @return the value of weekIncome
     */
    public BigDecimal getWeekIncome() {
        return weekIncome;
    }

    /**
     * Sets the weekIncome.
     *
     * <p>You can use getWeekIncome() to get the value of weekIncome</p>
     *
     * @param weekIncome weekIncome
     */
    public void setWeekIncome(BigDecimal weekIncome) {
        this.weekIncome = weekIncome;
    }

    /**
     * Gets the value of monthIncome.
     *
     * @return the value of monthIncome
     */
    public BigDecimal getMonthIncome() {
        return monthIncome;
    }

    /**
     * Sets the monthIncome.
     *
     * <p>You can use getMonthIncome() to get the value of monthIncome</p>
     *
     * @param monthIncome monthIncome
     */
    public void setMonthIncome(BigDecimal monthIncome) {
        this.monthIncome = monthIncome;
    }

    /**
     * Gets the value of totalIncome.
     *
     * @return the value of totalIncome
     */
    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    /**
     * Sets the totalIncome.
     *
     * <p>You can use getTotalIncome() to get the value of totalIncome</p>
     *
     * @param totalIncome totalIncome
     */
    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
}
