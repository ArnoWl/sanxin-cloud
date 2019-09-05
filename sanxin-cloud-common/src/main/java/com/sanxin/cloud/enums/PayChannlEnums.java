package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付渠道枚举类
 * @author xiaoky
 * @date 2019-05-28
 */
public enum PayChannlEnums {
	APP(1,"APP"),
	ALI_PROGRAM(2,"Ali Program");

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

	private PayChannlEnums(Integer id,String name){
		this.id=id;
		this.name=name;
	}

	public static List<PayChannlEnums> queryList(){
		List<PayChannlEnums> list=new ArrayList<PayChannlEnums>();
		for(PayChannlEnums o:PayChannlEnums.values()){
			list.add(o);
		}
		return list;
	}

	public static String getName(Integer id){
		String str="";
		for(PayChannlEnums o:PayChannlEnums.values()){
			if(FunctionUtils.isEquals(id, o.getId())){
				str= o.getName();
				break;
			}
		}
		return str;
	}
}

