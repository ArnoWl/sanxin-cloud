package com.sanxin.cloud.dto;

/**
 * 根据时间查询vo
 * @author xiaoky
 * @date 2019-09-20
 */
public class QueryTimeDataVo {

    /**
     * 查询数据id
     */
    private Integer targetId;
    /**
     * 类型 日期，星期，月份
     */
    private Integer type;
    /**
     * 查询时间
     */
    private String date;
    /**
     * 结束时间
     */
    private String endDate;

    /**
     * Gets the value of targetId.
     *
     * @return the value of targetId
     */
    public Integer getTargetId() {
        return targetId;
    }

    /**
     * Sets the targetId.
     *
     * <p>You can use getTargetId() to get the value of targetId</p>
     *
     * @param targetId targetId
     */
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    /**
     * Gets the value of type.
     *
     * @return the value of type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * <p>You can use getType() to get the value of type</p>
     *
     * @param type type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets the value of date.
     *
     * @return the value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * <p>You can use getDate() to get the value of date</p>
     *
     * @param date date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the value of endDate.
     *
     * @return the value of endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the endDate.
     *
     * <p>You can use getEndDate() to get the value of endDate</p>
     *
     * @param endDate endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
