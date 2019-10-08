package com.sanxin.cloud.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 加盟商
 * @author xiaoky
 * @date 2019-09-21
 */
@Data
public class OrderBusVo {
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 充电宝id
     */
    private String terminalId;
    /**
     * 订单状态 见OrderStatusEnums
     */
    private Integer orderStatus;
    /**
     * 订单状态名称
     */
    private String statusName;
    /**
     * 租借人
     */
    private String cusName;
    /**
     * 使用时长
     */
    private String useHour;
    /**
     * 预计租金
     */
    private BigDecimal estimatedRentMoney;

    /**
     * 是否购买充电宝 1购买了 0未购买(正常借还)
     */
    private Integer buy;

    /**
     * 购买充电宝价格
     */
    private BigDecimal terminalMoney;
    /**
     * 购买充电宝扣除押金金额
     */
    private BigDecimal depositMoney;
    /**
     * 租借时间
     */
    private Date rentTime;
    /**
     * 租借地点
     */
    private String addressDetail;
}
