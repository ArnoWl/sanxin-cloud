package com.sanxin.cloud.dto;

/**
 * 注册登录统一处理 vo对象
 *
 * @author xiaoky
 * @date 2019-09-16
 */
public class LoginRegisterVo {

    /** 登录渠道 见LoginChannelEnums */
    private Integer channel;
    /** 登录类型 标识用户登录/加盟商登录  1 用户  2 加盟商 */
    private Integer type;
    /** 登录账号 */
    private String phone;
    /** 登录密码 */
    private String passWord;
    /** 验证码 */
    private String validCode;
    /**
     * 支付宝小程序登录授权code
     */
    private String authCode;

    /** 邀请码（推荐人的）*/
    private String inviteCode;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
