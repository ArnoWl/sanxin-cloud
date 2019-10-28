package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 地址基础表
 * </p>
 *
 * @author Arno
 * @since 2019-10-28
 */
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parentid;

    /**
     * 中文名
     */
    private String name;

    /**
     * 泰语
     */
    private String nameThai;

    /**
     * 英文
     */
    private String nameEn;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 0无效 1有效
     */
    private Integer status;

    /**
     * 译文
     */
    @TableField(exist = false)
    private String Translation;

    public String getTranslation() {
        return Translation;
    }

    public void setTranslation(String translation) {
        Translation = translation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNameThai() {
        return nameThai;
    }

    public void setNameThai(String nameThai) {
        this.nameThai = nameThai;
    }
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", parentid=" + parentid +
                ", name='" + name + '\'' +
                ", nameThai='" + nameThai + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", level=" + level +
                ", status=" + status +
                ", Translation='" + Translation + '\'' +
                '}';
    }
}
