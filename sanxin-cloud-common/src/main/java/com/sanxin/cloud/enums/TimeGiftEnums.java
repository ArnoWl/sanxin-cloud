package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zdc
 * @date 2019-11-4
 */
public enum TimeGiftEnums {
    /**
     * 购买
     */
    BUY(1, "time_buy"),
    /**
     * 注册赠送
     */
    GIFT(2, "time_gift"),
    /**
     * 数量
     */
    NUM(3, "time_num"),
    /**
     * 时间
     */
    TIME(4, "time_time");

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


