package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xiaoky
 * @date 2019-09-12
 */
public enum HandleTypeEnums {
    CASH(1, "handle_type_cash"),
    ORDER(2, "handle_type_order");

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


