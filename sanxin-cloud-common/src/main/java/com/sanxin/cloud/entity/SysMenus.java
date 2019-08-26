package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台菜单表
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@Data
@Accessors(chain = true)
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
    private String title;

    /**
     * 与视图的文件夹名称和路由路径对应
     */
    private String name;

    /**
     * 1有效  0无效
     */
    private Integer status;

    /**
     * 菜单图标样式
     */
    private String icon;

    /**
     * 自定义菜单路由地址
     */
    private String jump;

    /**
     * 排序  从大到小降序
     */
    private Integer sort;

    /**
     * 菜单类型 1菜单 2权限 3开发
     */
    private Integer type;


}
