package com.sanxin.cloud.common.regular;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author Arno
 *
 */
public class RpxUtils {

	private static RpxUtils rpxUtils = null;

	public static RpxUtils getInstance() {
		if (rpxUtils == null) {
			synchronized (RpxUtils.class) {
				if (rpxUtils == null) {
					rpxUtils = new RpxUtils();
				}
			}
		}
		return rpxUtils;
	}

	/**
	 * 密码校验正则表达式
	 */
	private final String passwor_rpx = "[a-zA-Z0-9]{6,20}";

	private final String nickname_rpx = "[`~!@#$%^&*()+=|{}':;'_,\\[\\].<>/?~！@#￥%……&*（）— —+|{}【】‘；：”“’。，、？-]";

	private final String url_rpx = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";

	private final String paypwd_rpx = "[\\d]{6}";

	/**
	 * url校验
	 * 
	 * @param url
	 * @return
	 */
	public String valid_url(String url) {
		if (url.matches(url_rpx)) {
			if (url.lastIndexOf("/") != url.length() - 1) {
				return "URL地址必须以/结束";
			}
			return "";
		} else {
			return "请输入正确的URL地址";
		}
	}

	/**
	 * 校验账号 只能为字母加数字的6-20位
	 * 
	 * @param login
	 * @return
	 */
	public String valid_login(String login) {
		if (login.matches(passwor_rpx)) {
			return "";
		} else {
			return "用户名只能为英文加数字,且在6-20位之间";
		}
	}

	/**
	 * 校验密码 只能为字母加数字的6-20位
	 * 
	 * @param password
	 * @return
	 */
	public String valid_password(String password) {
		if (password.matches(passwor_rpx)) {
			return "";
		} else {
			return "密码只能为英文加数字,且在6-20位之间";
		}
	}

	/**
	 * 校验昵称只能在20位以内，且不能包干特殊字符
	 * 
	 * @param nickname
	 * @return
	 */
	public String valid_nickname(String nickname, String ext) {
		nickname = nickname.trim();
		Pattern p = Pattern.compile(nickname_rpx);
		Matcher m = p.matcher(nickname);
		if (nickname.length() > 20) {
			return "昵称只能在20位以内,且不能包含特殊字符";
		}
		if (m.find()) {
			return "昵称只能在20位以内,且不能包含特殊字符";
		} else {
			return "";
		}
	}

	/**
	 * 校验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public String valid_phone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return "	请输入手机号";
		}
		if (phone.length() != 11) {
			return "	手机号长度不正确";
		}
		//String regex = "^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][0-9])|([7][0-9])|([8][0-9])|([9][0-9]))[0-9]{8}$";
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		//System.out.println(regex);
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phone);
		boolean isMatch = m.matches();
		if (isMatch) {
			return "";
		} else {
			return "手机号格式不正确";
		}
	}

	/**
	 * 校验交易密码
	 * 
	 * @param paypwd
	 * @param ext
	 * @return
	 */
	public String valid_paypwd(String paypwd, String ext) {
		Pattern p = Pattern.compile(paypwd_rpx);
		Matcher m = p.matcher(paypwd);
		boolean isMatch = m.matches();
		if (StringUtils.isEmpty(paypwd)) {
			return "交易密码为6位数字";
		} else {
			if (isMatch) {
				return "";
			} else {
				return "交易密码为6位数字";
			}
		}
	}

	/**
	 * 校验发帖内容
	 * 
	 * @param content
	 * @param ext
	 */
	public final String valid_content(String content, String ext) {
		if (!StringUtils.isBlank(content)) {
			if (content.length() > 200) {
				return "内容文本不得超过200位";
			}
		}
		return "";
	}

	/**
	 * 校验评论回复内容
	 * 
	 * @param content
	 * @param ext
	 */
	public final String valid_comment(String content, String ext) {
		if (!StringUtils.isEmpty(content)) {
			if (content.length() > 100) {
				return "评价内容需为100字以内";
			}
		} else {
			return "评价内容需为100字以内";
		}
		return "";
	}

	/***
	 * 验证字符串中是否有非法字符
	 * 
	 * @param content
	 * @return true:不包含非法字符 false:包含非法字符
	 */
	public boolean verification(String content) {
		String regEx = "[ `!@#$%^()+={}':;',\\[\\]<>/?！@-#￥%……（）+{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		return !m.find();
	}
	
	/**
	 * 验证邮箱格式
	 * @param email
	 * @return
	 */
	public boolean valid_email(String email) { 
		 String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
		 Pattern regex = Pattern.compile(check);  
		 Matcher matcher = regex.matcher(email);  
		 boolean isMatched = matcher.matches();  
		 return isMatched;
	}
}
