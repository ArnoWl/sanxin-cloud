package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoky
 * @description 支付方式枚举
 * @date 2019-09-04
 */
public enum PayTypeEnums {
    MONEY(1, "Balance", 1, 4,1),
    PROMPT_PAY(2, "Prompt Pay", 1, 4,4),
    VISA_CARD(3, "VISA Card", 1, 4,2),
    MASTER_CARD(4, "Master Card", 1, 4,2),
    GOOGLE_PAY(5, "Google Pay", 1, 4,2),
    APPLE_PAY(6, "Apple Pay", 1, 4,2),
    ALI_PAY(7, "Alipay Pay", 1, 4,2);

    private Integer id;

    private String name;

    /**
     * 是否可用
     */
    private Integer status;
    /**
     * 1表示是充值可以用 ，2是订单可以用，3是交押金可以用，4是通用
     */
    private Integer type;

    /**
     * 是否免密
     */
    private Integer freeSecret;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFreeSecret() {
        return freeSecret;
    }

    public void setFreeSecret(Integer freeSecret) {
        this.freeSecret = freeSecret;
    }

    private PayTypeEnums(Integer id, String name, Integer status, Integer type,Integer freeSecret) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.type = type;
        this.freeSecret = freeSecret;
    }

    public static String getName(Integer id) {
        String str = "";
        for (PayTypeEnums o : PayTypeEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getId())) {
                str = o.getName();
                break;
            }
        }
        return str;
    }

    public static List<PayTypeEnums> queryList() {
        List<PayTypeEnums> list = new ArrayList<PayTypeEnums>();
        for (PayTypeEnums o : PayTypeEnums.values()) {
            list.add(o);
        }
        return list;
    }

    public static PayTypeEnums getEnums(Integer paytype) {
        for (PayTypeEnums o : PayTypeEnums.values()) {
            if (FunctionUtils.isEquals(paytype, o.getId())) {
                return o;
            }
        }
        return null;
    }

    public static boolean isPay(Integer id) {
        for (PayTypeEnums o : PayTypeEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getId())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<PayTypeEnums> payTypeEnums = queryList();
        System.out.println(payTypeEnums.toString());
    }
}
