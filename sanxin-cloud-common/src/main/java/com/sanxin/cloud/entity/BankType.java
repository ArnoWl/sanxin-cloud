package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 银行卡类型表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public class BankType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 国家id
     */
    private Integer countryId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * logo
     */
    private String logo;

    /**
     * 1有效,0无效
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BankType{" +
        "id=" + id +
        ", countryId=" + countryId +
        ", bankName=" + bankName +
        ", logo=" + logo +
        ", status=" + status +
        "}";
    }
}
