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
	PROMPT_PAY(1,"Prompt Pay"),
	VISA_CARD(2,"VISA Card"),
	MASTER_CARD(3, "Master Card"),
	GOOGLE_PAY(4, "Google Pay"),
	APPLE_PAY(4, "Apple Pay"),
	ALI_PAY(4, "Alipay Pay");

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

	private PayTypeEnums(Integer id,String name){
		this.id=id;
		this.name=name;
	}

	public static String getName(Integer id){
		String str="";
		for(PayTypeEnums o:PayTypeEnums.values()){
			if(FunctionUtils.isEquals(id, o.getId())){
				str= o.getName();
				break;
			}
		}
		return str;
	}

	public static List<PayTypeEnums> queryList(){
		List<PayTypeEnums> list=new ArrayList<PayTypeEnums>();
		for(PayTypeEnums o:PayTypeEnums.values()){
			list.add(o);
		}
		return list;
	}

	public static PayTypeEnums getEnums(Integer paytype){
		for(PayTypeEnums o:PayTypeEnums.values()){
			if(FunctionUtils.isEquals(paytype, o.getId())){
				return o;
			}
		}
		return null;
	}

	public static boolean isPay(Integer id) {
		for(PayTypeEnums o:PayTypeEnums.values()){
			if(FunctionUtils.isEquals(id, o.getId())){
				return true;
			}
		}
		return false;
	}
}
