package com.sanxin.cloud.service.system.login;

import com.sanxin.cloud.common.times.DateUtil;

/**
 * 会员登录对象
 * @author arno
 *
 */
public class LoginDto {

	private Integer tid;
	/** 登录类型  1 用户  2 加盟商*/
	private Integer type;
	/** 登录渠道 见LoginChannelEnums枚举 */
	private Integer channel;
	/** 支付宝小程序 唯一编号 */
	private String userId;
	/** 时间戳 */
	private long timestamp= DateUtil.currentTimeMillis();
	/** 1不需要过期只限定APP PC H5  0需要过期 */
	private int needTimeout=1;
	/** 默认登陆30天有效期 */
	private long timemout=30*24*3600;




   private static LoginDto  loginDto = null;
   public static LoginDto getInstance(){
	   if(loginDto == null){
	   synchronized(LoginDto.class){
	     if(loginDto == null){
	    	 loginDto = new LoginDto();
	       }
	     }
	        return loginDto;
	    }
	return loginDto;
   }

	private LoginDto() {

	}

	/**
	 * 创建APP，PC，H5对象
	 * @param tid 用户id
	 */
	private LoginDto(Integer tid) {
		this.tid=tid;
	}

	/**
	 * 创建支付宝小程序对象
	 * @param tid
	 * @param userId
	 */
	private LoginDto(Integer tid, String userId) {
		this.tid=tid;
		this.userId = userId;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getNeedTimeout() {
		return needTimeout;
	}
	public void setNeedTimeout(int needTimeout) {
		this.needTimeout = needTimeout;
	}
	public long getTimemout() {
		return timemout;
	}
	public void setTimemout(long timemout) {
		this.timemout = timemout;
	}
}
