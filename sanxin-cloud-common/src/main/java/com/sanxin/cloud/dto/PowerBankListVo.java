package com.sanxin.cloud.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PowerBankListVo {
    /**
     * Id
     */
    private Integer id;
    /**
     * 设备编号
     */
    private String code;
    /**
     * 商铺名称
     */
    private String companyName;
    /**
     * 头像地址
     */
    private String headUrl;
    /**
     * 营业时间——开始日期(星期)
     */
    private Integer startDay;
    /**
     * 营业时间——结束日期(星期)
     */
    private Integer endDay;
    /**
     * 营业时间——开始时间(小时)
     */
    private String startTime;
    /**
     * 营业时间——结束时间(小时)
     */
    private String endTime;
    /**
     * 可以借出的口(充电宝)
     */
    private Integer lendPort;
    /**
     * 可以归还的口(充电宝)
     */
    private Integer repayPort;
    /**
     *可以借出的口(提示)
     */
    private String strLendPort;
    /**
     * 可以归还的口(提示)
     */
    private String strRepayPort;
    /**
     * 大机柜 小机柜
     */
    @TableField(exist = false)
    private String cabinet;
    /**
     * 详细地址
     */
    private String addressDetail;
    /**
     * 距离
     */
    private Integer distance;
    /**
     * 距离千米
     */
    private Double distanceKm;
    /**
     * 距离米
     */
    private Integer distanceM;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态 1 连接中  2 连接成功(运行中)  3 关闭(暂停)
     */
    private Integer status;
    /**
     * banner图
     */
    private String coverUrl;
    /**
     * 营业时间
     */
    private String businessHours;
    /**
     * 纬度
     */
    private String latVal;
    /**
     * 经度
     */
    private String lonVal;

    /**
     * 表示这个商家有几个机器
     */
    private Integer num;

}
