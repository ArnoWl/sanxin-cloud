package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 广告内容表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-04
 */
public class AAdvertContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 首页弹窗信息标题
     */
    private String title;

    /**
     * 首页弹窗信息内容
     */
    private String content;

    /**
     * 事件类型   见EventEnums
     */
    private String event;
    /**
     * 跳转值
     */
    private String url;
    /**
     * 首页弹窗图片
     */
    private String frameImg;

    /**
     * 是否有效 0 无效   1 有效
     */
    private Integer status;

    /**
     * 首页是否展示 0 不展示 1 展示(只有一个广告是展示状态)
     */
    private Integer homeShow;

    /**
     * 发布时间
     */
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
     * 泰语标题
     */
    @TableField(exist = false)
    private String thaiTitle;
    /**
     * 中文内容
     */
    @TableField(exist = false)
    private String cnContent;
    /**
     * 英文内容
     */
    @TableField(exist = false)
    private String enContent;
    /**
     * 泰语内容
     */
    @TableField(exist = false)
    private String thaiContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrameImg() {
        return frameImg;
    }

    public void setFrameImg(String frameImg) {
        this.frameImg = frameImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHomeShow() {
        return homeShow;
    }

    public void setHomeShow(Integer homeShow) {
        this.homeShow = homeShow;
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

    @Override
    public String toString() {
        return "AAdvertContent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", event='" + event + '\'' +
                ", url='" + url + '\'' +
                ", frameImg='" + frameImg + '\'' +
                ", status=" + status +
                ", homeShow=" + homeShow +
                ", createTime=" + createTime +
                ", cnTitle='" + cnTitle + '\'' +
                ", enTitle='" + enTitle + '\'' +
                ", thaiTitle='" + thaiTitle + '\'' +
                ", cnContent='" + cnContent + '\'' +
                ", enContent='" + enContent + '\'' +
                ", thaiContent='" + thaiContent + '\'' +
                '}';
    }
}
