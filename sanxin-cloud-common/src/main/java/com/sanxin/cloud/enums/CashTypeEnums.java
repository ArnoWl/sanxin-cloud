package com.sanxin.cloud.enums;

import java.util.ArrayList;
import java.util.List;

import com.sanxin.cloud.common.FunctionUtils;

/**
 * 提现类型枚举类
 * @author xiaoky
 * @date 2019-05-31
 */
public enum CashTypeEnums {
	CUSTOMER(1, "会员"),
	BUSINESS(2, "商家");

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

	private CashTypeEnums(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public static List<CashTypeEnums> queryList() {
        List<CashTypeEnums> list = new ArrayList<CashTypeEnums>();
        for (CashTypeEnums o : CashTypeEnums.values()) {
            list.add(o);
        }
        return list;
    }

    public static String getName(Integer id) {
        String str = "";
        for (CashTypeEnums o : CashTypeEnums.values()) {
            if (FunctionUtils.isEquals(id, o.getId())) {
                str = o.getName();
                break;
            }
        }
        return str;
    }
}
