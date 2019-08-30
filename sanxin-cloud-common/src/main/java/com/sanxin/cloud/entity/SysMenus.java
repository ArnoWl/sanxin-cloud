package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 平台菜单表
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@TableName("sys_menus")
public class SysMenus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级id  0表示父级
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单url(若是以&_blank结尾, 表示此页面在新窗口打开)
     */
    private String url;

    /**
     * 1有效  0无效
     */
    private Integer status;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序  从大到小降序
     */
    private Integer sort;

    /**
     * 菜单类型 1菜单 2权限 3开发
     */
    private Integer type;

    @TableField(exist = false)
    private List<SysMenus> childList;

    @TableField(exist = false)
    private String menuname;

    @TableField(exist = false)
    private String checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    @Override
    public String toString() {
        return "SysMenus{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", name=" + name +
        ", url=" + url +
        ", status=" + status +
        ", icon=" + icon +
        ", sort=" + sort +
        ", type=" + type +
        "}";
    }

    public List<SysMenus> getChildList() {
            return childList;
    }

    public void setChildList(List<SysMenus> childList) {
        this.childList = childList;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
