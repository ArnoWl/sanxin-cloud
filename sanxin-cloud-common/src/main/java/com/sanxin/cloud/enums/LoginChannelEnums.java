package com.sanxin.cloud.enums;

import com.sanxin.cloud.common.FunctionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员注册/登录渠道 包含订单下单渠道
 * @author arno
 *
 */
public enum LoginChannelEnums {

	APP(1,"APP"),
	ALI_PROGRAM(2,"ali program");

	private Integer channel;
	private String name;

	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private LoginChannelEnums(Integer channel,String name) {
		this.channel=channel;
		this.name=name;
	}

	public static String getName(Integer channel) {
		String name="";
		for(LoginChannelEnums channelEnums:LoginChannelEnums.values()) {
			if(FunctionUtils.isEquals(channelEnums.getChannel(), channel)) {
				name=channelEnums.getName();
				break;
			}
		}
		return name;
	}

	public static LoginChannelEnums getLoginEnum(int loginChannel){
		switch (loginChannel){
			case 1:
				return APP;
			case 2:
				return ALI_PROGRAM;
			default:
				return null;
		}
	}

	public static List<LoginChannelEnums> queryList(){
		List<LoginChannelEnums> list=new ArrayList<LoginChannelEnums>();
		for(LoginChannelEnums o: LoginChannelEnums.values()){
			list.add(o);
		}
		return list;
	}

}
