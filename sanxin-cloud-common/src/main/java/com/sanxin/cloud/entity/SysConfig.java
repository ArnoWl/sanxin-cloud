package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
@Data
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 键名
     */
    private String keyval;

    /**
     * 参数
     */
    private String vals;

    /**
     * 描述
     */
    private String remark;

    /**
     * 0启用 -1禁用
     */
    private Integer status;


}
