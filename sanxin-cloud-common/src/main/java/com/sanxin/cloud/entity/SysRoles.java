package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author arno
 * @since 2019-08-25
 */
public class SysRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色菜单权限逗号分隔字符串
     */
    private String menuIds;
    /**
     * 1有效 0无效
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 目标id
     */
    private Integer targetId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "SysRoles{" +
        ", id=" + id +
        ", name=" + name +
        ", menuIds=" + menuIds +
        ", status=" + status +
        ", createtime=" + createtime +
        ", targetId=" + targetId +
        "}";
    }
}
