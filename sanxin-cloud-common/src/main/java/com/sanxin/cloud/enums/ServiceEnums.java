package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

/**
 * 业务处理枚举类(暂时只是用来标识payCode前缀)
 * @author xiaoky
 * @date 2019-09-12
 */
public enum ServiceEnums {
    ORDER(1,"service_order"),
    RECHARGE_DEPOSIT_MONEY(2,"service_recharge_deposit"),
    CASH(3, "handle_type_cash"),
    BUY_TIEM_GIFT(4, "buy_type_time");

    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private  ServiceEnums(int id,String name) {
        this.id=id;
        this.name=name;
    }

    public static String getName(int id) {
        for(ServiceEnums e:ServiceEnums.values()) {
            if(id==e.getId()) {
                return LanguageUtils.getMessage(e.getName());
            }
        }
        return "";
    }

    public static ServiceEnums getEnums(Integer id){
        for(ServiceEnums o:ServiceEnums.values()){
            if(FunctionUtils.isEquals(id, o.getId())){
                return o;
            }
        }
        return null;
    }
}
