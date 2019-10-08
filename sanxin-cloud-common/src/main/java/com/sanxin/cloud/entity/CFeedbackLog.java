package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 故障反馈
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-11
 */
@Data
public class CFeedbackLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer cid;

    /**
     * 店铺id  null  即视为平台管理该反馈
     */
    private Integer bid;

    /**
     * 反馈意见内容
     */
    private String content;

    /**
     * 故障反馈图片
     */
    private String backUrl;

    /**
     * 图片反馈图片
     */
    @TableField(exist = false)
    private List<String> url;

    /**
     * 0  未解决   1 已解决
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

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

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
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
        return "CFeedbackLog{" +
                "id=" + id +
                ", cid=" + cid +
                ", bid=" + bid +
                ", content='" + content + '\'' +
                ", backUrl='" + backUrl + '\'' +
                ", url=" + url +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
