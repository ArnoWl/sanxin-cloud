package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 平台用户表
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录
     */
    private String login;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createtime;

    private Integer roleid;

    /**
     * 头像
     */
    private String headurl;

    /**
     * 1有效 0无效
     */
    private Integer status;
    /**
     * 是否代理  1 是  0 不是
     */
    private Integer isAg;
    /**
     * 代理id
     */
    private Integer agId;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String rolename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }
    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsAg() {
        return isAg;
    }

    public void setIsAg(Integer isAg) {
        this.isAg = isAg;
    }

    public Integer getAgId() {
        return agId;
    }

    public void setAgId(Integer agId) {
        this.agId = agId;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        "id=" + id +
        ", login=" + login +
        ", password=" + password +
        ", name=" + name +
        ", phone=" + phone +
        ", createtime=" + createtime +
        ", roleid=" + roleid +
        ", headurl=" + headurl +
        ", status=" + status +
        "}";
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
