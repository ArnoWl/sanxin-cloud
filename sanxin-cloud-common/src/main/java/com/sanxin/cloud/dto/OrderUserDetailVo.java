package com.sanxin.cloud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情封装类
 * @author xiaoky
 * @date 2019-09-23
 */
@Data
public class OrderUserDetailVo extends OrderUserVo{

    /**
     * 计费标准
     */
    private String standard;

    /**
     * 租借总额
     */
    private BigDecimal rentMoney;

    /**
     * 租借时长
     */
    private Integer hour;

    /**
     * 扣除余额
     */
    private BigDecimal money;

    /**
     * 归还时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date returnTime;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 支付方式名称
     */
    private String payTypeName;

    /**
     * 实际支付
     */
    private BigDecimal realMoney;

    /**
     * 支付时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date payTime;

    /**
     * 租借地点
     */
    private String rentAddr;
    /**
     * 归还地点
     */
    private String returnAddr;

    /**
     * 扣除时长
     */
    private String subTime;

    /**
     * 计费规则
     */
    private String billingRule;
}
