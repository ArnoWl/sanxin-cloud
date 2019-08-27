package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 平台操作日志
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@TableName("sys_logs")
public class SysLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 当前操作人
     */
    @TableField("login_account")
    private String loginAccount;

    /**
     * 当前操作人IP
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     *  操作请求的链接
     */
    @TableField("action_url")
    private String actionUrl;

    /**
     *  执行的模块
     */
    private String module;

    /**
     * 执行的方法
     */
    private String method;

    /**
     * 执行操作时间
     */
    @TableField("action_time")
    private Long actionTime;

    /**
     * 描述
     */
    private String description;

    /**
     * 执行的时间
     */
    private Date createtime;

    /**
     * 该操作状态，1表示成功，0表示失败
     */
    private Integer status;

    /**
     * 1总平台
     */
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public Long getActionTime() {
        return actionTime;
    }

    public void setActionTime(Long actionTime) {
        this.actionTime = actionTime;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysLogs{" +
        "id=" + id +
        ", loginAccount=" + loginAccount +
        ", loginIp=" + loginIp +
        ", actionUrl=" + actionUrl +
        ", module=" + module +
        ", method=" + method +
        ", actionTime=" + actionTime +
        ", description=" + description +
        ", createtime=" + createtime +
        ", status=" + status +
        ", type=" + type +
        "}";
    }
}
