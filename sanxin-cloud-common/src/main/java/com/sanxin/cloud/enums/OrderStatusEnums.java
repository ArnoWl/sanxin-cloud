package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单状态枚举
 * @author xiaoky
 * @date 2019-09-21
 */
public enum OrderStatusEnums {
    USING(1, "order_status_using"),
    CONFIRMED(2, "order_status_confirmed"),
    OVER(3, "order_status_over");

    private Integer id;

    private String name;

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

    private OrderStatusEnums(Integer id,String name){
        this.id=id;
        this.name=name;
    }

    public static List<OrderStatusEnums> queryList(){
        List<OrderStatusEnums> list=new ArrayList<OrderStatusEnums>();
        for(OrderStatusEnums o:OrderStatusEnums.values()){
            list.add(o);
        }
        return list;
    }

    public static String getName(Integer id){
        String str="";
        for(OrderStatusEnums o:OrderStatusEnums.values()){
            if(FunctionUtils.isEquals(id, o.getId())){
                str= o.getName();
                break;
            }
        }
        return str;
    }
}
