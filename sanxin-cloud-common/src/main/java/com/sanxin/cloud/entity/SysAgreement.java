package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 协议内容
     */
    private String content;

    private Date createTime;

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
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysAgreement{" +
        "id=" + id +
        ", type=" + type +
        ", title=" + title +
        ", content=" + content +
        ", createTime=" + createTime +
        "}";
    }
}
