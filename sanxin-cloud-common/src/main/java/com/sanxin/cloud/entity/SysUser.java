package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台用户表
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@Data
@Accessors(chain = true)
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


}
