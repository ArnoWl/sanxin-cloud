package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 基础键值对配置表
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-08
 */
public class InfoParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * key
     */
    private String kcode;

    /**
     * 参数
     */
    private String kvalue;

    private String remark;

    /**
     * 参数类型 1:int 2:decimal 3:string
     */
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getKcode() {
        return kcode;
    }

    public void setKcode(String kcode) {
        this.kcode = kcode;
    }
    public String getKvalue() {
        return kvalue;
    }

    public void setKvalue(String kvalue) {
        this.kvalue = kvalue;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InfoParam{" +
        "id=" + id +
        ", kcode=" + kcode +
        ", kvalue=" + kvalue +
        ", remark=" + remark +
        ", type=" + type +
        "}";
    }
}
