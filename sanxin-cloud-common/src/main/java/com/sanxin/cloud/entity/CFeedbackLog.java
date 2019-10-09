package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 0  未解决   1 已解决
     */
    private Integer status;

    private Date createTime;

    @TableField(exist = false)
    private String bussinessName;

    @TableField(exist = false)
    private String realName;

    /**
     * 图片反馈图片
     */
    @TableField(exist = false)
    private List<String> url;

    public List<String> getUrl() {
        if(backUrl != null){
            if(backUrl.contains(",")){
                url = Arrays.asList(backUrl.split(","));
            }else{
                url = new ArrayList<>();
                url.add(backUrl);
            }
        }
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
        ", content=" + content +
        ", backUrl=" + backUrl +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
