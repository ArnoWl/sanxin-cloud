package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoky
 * @date 2019-09-12
 */
public enum TimeGiftEnums {
    /**
     * 订单
     */
    ORDER(1, "handle_type_order"),
    /**
     * 充值押金
     */
    RECHARGE_DEPOSIT_MONEY(2, "service_recharge_deposit"),
    /**
     * 提现
     */
    CASH(3, "handle_type_cash"),
    /**
     * 扣除时长
     */
    TIME(4, "handle_type_time"),
    /**
     * 购买时长礼包
     */
    BUY_TIEM_GIFT(5, "buy_type_time"),
    /**
     * 购买充电宝
     */
    BUY_POWER(6, "handle_type_buy_power"),
    /**
     * 赠送时长
     */
    TIME_GIFT(7, "time_gift");

    private Integer id;
    private String name;

    TimeGiftEnums(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<TimeGiftEnums> queryList() {
        List<TimeGiftEnums> list = new ArrayList<TimeGiftEnums>();
        for (TimeGiftEnums o : TimeGiftEnums.values()) {
            list.add(o);
        }
        return list;
    }

    public static String getName(Integer id) {
        String str = "";
        for (TimeGiftEnums o : TimeGiftEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getId())) {
                str = LanguageUtils.getMessage(o.getName());
                break;
            }
        }
        return str;
    }
}


