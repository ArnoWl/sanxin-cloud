package com.sanxin.cloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 发现广告表
 * </p>
 *
 * @author Arno
 * @since 2019-10-18
 */
public class AAdvertFind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 图片地址
     */
    private String img;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 0横向1纵向
     */
    private Integer type;

    /**
     * 是否有效(0 无效 1有效)
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "AAdvertFind{" +
        "id=" + id +
        ", url=" + url +
        ", img=" + img +
        ", sort=" + sort +
        ", type=" + type +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
