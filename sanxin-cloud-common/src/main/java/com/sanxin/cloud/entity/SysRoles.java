package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * 系统角色表
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@Data
@Accessors(chain = true)
@TableName("sys_roles")
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
    @TableField("menu_ids")
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
    @TableField("target_id")
    private Integer targetId;


}
