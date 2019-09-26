package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author Arno
 * @since 2019-09-17
 */
public class CPushLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息对应者Id
     */
    private Integer targetId;

    /**
     * 交易单号
     */
    private String payCode;

    /**
     * 消息类型  见MsgUtils工具类
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 外键 可能用来处理事件需要的参数
     */
    private String targetCode;

    /**
     * 0未读 1已读
     */
    private Integer reading;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * 中文全内容
     */
    private String remark;

    /**
     * 逗号分隔内容
     */
    private String params;

    /**
     * 消息对象类型  标识用户消息或加盟商消息  见CashTypeEnums
     */
    private Integer targetType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return "CPushLog{" +
        "id=" + id +
        ", targetId=" + targetId +
        ", payCode=" + payCode +
        ", msgType=" + msgType +
        ", content=" + content +
        ", targetCode=" + targetCode +
        ", reading=" + reading +
        ", createTime=" + createTime +
        ", remark=" + remark +
        ", params=" + params +
        ", targetType=" + targetType +
        "}";
    }
}
