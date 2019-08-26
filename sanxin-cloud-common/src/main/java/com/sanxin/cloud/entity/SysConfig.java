package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Arno
 * @since 2019-08-26
 */
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getKeyval() {
        return keyval;
    }

    public void setKeyval(String keyval) {
        this.keyval = keyval;
    }
    public String getVals() {
        return vals;
    }

    public void setVals(String vals) {
        this.vals = vals;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
        "id=" + id +
        ", keyval=" + keyval +
        ", vals=" + vals +
        ", remark=" + remark +
        ", status=" + status +
        "}";
    }
}
