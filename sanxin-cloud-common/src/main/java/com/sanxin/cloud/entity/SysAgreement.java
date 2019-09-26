package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 系统协议
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-31
 */
public class SysAgreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用于标识协议
     */
    private Integer type;

    /**
     * 协议标题
     */
    private String title;

    /**
     * 中文协议内容
     */
    private String cnContent;

    /**
     * 英文协议内容
     */
    private String enContent;

    /**
     * 泰文协议内容
     */
    private String thaiContent;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    /**
     * 中文标题
     */
    @TableField(exist = false)
    private String cnTitle;
    /**
     * 英文标题
     */
    @TableField(exist = false)
    private String enTitle;
    /**
     * 泰文标题
     */
    @TableField(exist = false)
    private String thaiTitle;
    /**
     * 接口方返回数据
     */
    @TableField(exist = false)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCnContent() {
        return cnContent;
    }

    public void setCnContent(String cnContent) {
        this.cnContent = cnContent;
    }

    public String getEnContent() {
        return enContent;
    }

    public void setEnContent(String enContent) {
        this.enContent = enContent;
    }

    public String getThaiContent() {
        return thaiContent;
    }

    public void setThaiContent(String thaiContent) {
        this.thaiContent = thaiContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCnTitle() {
        return cnTitle;
    }

    public void setCnTitle(String cnTitle) {
        this.cnTitle = cnTitle;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public String getThaiTitle() {
        return thaiTitle;
    }

    public void setThaiTitle(String thaiTitle) {
        this.thaiTitle = thaiTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SysAgreement{" +
        "id=" + id +
        ", type=" + type +
        ", title=" + title +
        ", cnContent=" + cnContent +
        ", enContent=" + enContent +
        ", thaiContent=" + thaiContent +
        ", createTime=" + createTime +
        "}";
    }
}
