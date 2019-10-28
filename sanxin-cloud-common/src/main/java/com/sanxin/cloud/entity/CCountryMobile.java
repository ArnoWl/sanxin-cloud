package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Arno
 * @since 2019-10-28
 */
public class CCountryMobile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 国家
     */
    private String country;

    /**
     * 区号
     */
    private Integer mobilePrefix;

    /**
     * 状态，1有效，0无效
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public Integer getMobilePrefix() {
        return mobilePrefix;
    }

    public void setMobilePrefix(Integer mobilePrefix) {
        this.mobilePrefix = mobilePrefix;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CCountryMobile{" +
        "id=" + id +
        ", country=" + country +
        ", mobilePrefix=" + mobilePrefix +
        ", status=" + status +
        "}";
    }
}
