package com.sanxin.cloud.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zdc
 * @date 2019-10-28
 */
public enum AddressEnums {
    THAI(1, "66","Chain"),
    ZH(2, "86","Thai");


    private Integer id;
    private String area;
    private String country;


    AddressEnums(Integer id, String area, String country) {
        this.id = id;
        this.area = area;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static List<AddressEnums> queryList() {
        List<AddressEnums> list = new ArrayList<>();
        for (AddressEnums o : AddressEnums.values()) {
            list.add(o);
        }
        return list;
    }

    public static List<AddressEnums> getList() {
        List<AddressEnums> list = new ArrayList<>();
        for (AddressEnums o : AddressEnums.values()) {
            list.add(o);
        }
        return list;
    }
}


