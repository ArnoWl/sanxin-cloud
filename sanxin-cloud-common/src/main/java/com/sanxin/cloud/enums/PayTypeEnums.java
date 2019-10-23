package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoky
 * @description 支付方式枚举
 * @date 2019-09-04
 */
public enum PayTypeEnums {
    /**
     * 支付方式
     */
    MONEY(1, "Balance", "http://47.106.131.191:8004//files/20191016/5295229.png", 1, 6, 1),
    SCB_PAY(2, "SCB", "http://47.106.131.191:8004//files/20191016/5250577.png", 1, 6, 0),
    VISA_CARD(3, "VISA Card", "http://47.106.131.191:8004//files/20191016/52834612.png", 1, 6, 0),
    MASTER_CARD(4, "LINE", "http://47.106.131.191:8004//files/20191016/52754475.png", 1, 6, 0),
    ALI_PAY(5, "Alipay Pay", "http://47.106.131.191:8004//files/20191016/53343674.png", 1, 6, 0),
    WE_CHAT_PAY(6, "WeChat Pay", "", 1, 6, 0),
    PROMPT_PAY(7, "Prompt Pay", "", 1, 4, 0),
    GOOGLE_PAY(8, "Prompt Pay", "", 0, 4, 0),
    APPLE_PAY(9, "Prompt Pay", "", 0, 4, 0);
    private Integer id;
    private String name;
    /**
     * 是否可用 0 不可用 1 可用
     */
    private Integer status;
    /**
     * 1 订单 ，2充值押金，3购买时长，4充值余额 5是通用
     */
    private Integer type;
    /**
     * logo
     */
    private String logo;
    /**
     * 是否需要校验支付密码 1 需要校验 0 不需要校验
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    private PayTypeEnums(Integer id, String name, String logo, Integer status, Integer type, Integer freeSecret) {
        this.id = id;
        this.name = name;
        this.logo = logo;
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

    /**
     * 查询所有支付方式
     * @return
     */
    public static List<PayTypeEnums> queryList() {
        List<PayTypeEnums> list = new ArrayList<PayTypeEnums>();
        for (PayTypeEnums o : PayTypeEnums.values()) {
            list.add(o);
        }
        return list;
    }

    /**
     * 通过支付页面类型控制支付方式
     * @return
     */
    public static List<Map<String, Object>> queryListByPayPage(Integer payPageType) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PayTypeEnums o : PayTypeEnums.values()) {
            if (FunctionUtils.isEquals(o.getStatus(), StaticUtils.STATUS_YES)
                    && (FunctionUtils.isEquals(payPageType, o.getType())
                    || FunctionUtils.isEquals(o.getType(), 6))) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", o.getId());
                map.put("name", o.getName());
                map.put("logo", o.getLogo());
                map.put("freeSecret", o.getFreeSecret());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 查询小程序支付方式
     * @return
     */
    public static List<Map<String, Object>> queryListForProgramByPayPage() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PayTypeEnums o : PayTypeEnums.values()) {
            if (FunctionUtils.isEquals(o.getStatus(), StaticUtils.STATUS_YES)
                    && (FunctionUtils.isEquals(o.getId(), 1)
                    || FunctionUtils.isEquals(o.getId(), 5))) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", o.getId());
                map.put("name", o.getName());
                map.put("logo", o.getLogo());
                map.put("freeSecret", o.getFreeSecret());
                list.add(map);
            }
        }
        return list;
    }

    public static PayTypeEnums getEnums(Integer type) {
        for (PayTypeEnums o : PayTypeEnums.values()) {
            if (FunctionUtils.isEquals(type, o.getId())) {
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
}
