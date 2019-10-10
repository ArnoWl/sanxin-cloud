package com.sanxin.cloud.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 余额明细表
 * </p>
 *
 * @author Arno
 * @since 2019-10-10
 */
@Data
public class CMoneyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer cid;

    /**
     * 关联单号
     */
    private String payCode;

    /**
     * 操作类型
     */
    private Integer type;

    /**
     * 1充值 0提现
     */
    private Integer isout;

    /**
     * 上一次金额
     */
    private BigDecimal original;

    /**
     * 本次操作金额
     */
    private BigDecimal cost;

    /**
     * 最后结余
     */
    private BigDecimal last;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * 描述
     */
    private String remark;

    /**
     * 昵称
     */
    @TableField(exist = false)
    private String nickName;


    public CMoneyDetail(Integer cid, Integer type, Integer isout, String payCode, BigDecimal cost, String remark) {
        this.cid = cid;
        this.type = type;
        this.isout = isout;
        this.payCode = payCode;
        this.cost = cost;
        this.remark = remark;
    }
}
