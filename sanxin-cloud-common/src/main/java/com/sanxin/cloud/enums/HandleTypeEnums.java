package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoky
 * @date 2019-09-12
 */
public enum HandleTypeEnums {
    ORDER(1, "handle_type_order"),
    RECHARGE_DEPOSIT_MONEY(2, "service_recharge_deposit"),
    CASH(3, "handle_type_cash"),
    TIME(4, "handle_type_time"),
    BUY_TIEM_GIFT(5, "buy_type_time");

    private Integer id;
    private String name;

    HandleTypeEnums(Integer id, String name) {
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

    public static List<HandleTypeEnums> queryList() {
        List<HandleTypeEnums> list = new ArrayList<HandleTypeEnums>();
        for (HandleTypeEnums o : HandleTypeEnums.values()) {
            list.add(o);
        }
        return list;
    }

    public static String getName(Integer id) {
        String str = "";
        for (HandleTypeEnums o : HandleTypeEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getId())) {
                str = LanguageUtils.getMessage(o.getName());
                break;
            }
        }
        return str;
    }
}


