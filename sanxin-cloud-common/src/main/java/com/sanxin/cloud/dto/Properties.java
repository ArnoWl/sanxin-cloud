package com.sanxin.cloud.dto;

/**
 * properties
 * @author arno
 *
 */
public class Properties {

	/** 邮件 key */
	public String accesskey;
	/** 邮件secret */
	public String secretkey;
	/** 发送方地址 */
	public String fromAlias;
	/** 短信签名的Id */
	public String signId;
	/** 短信模板Id */
	public String templateId ;

	/** 七牛Ak秘钥 */
	public String qiniuAk;
	/** 七牛Sk */
	public String qiniuSk;
	/** 文件桶 */
	public String bucket;
	/** 七牛域名 */
	public String domain;

	/** 短信Key */
	public String smsAppkey;
	/** 验证码模板 */
	public String smsValidmodelcode;

	/**  */
	public String rsaFilepath;

	/** api项目域名 包含APP小程序 */
	public String apiDomain;
	/** 手机网页域名微信公众号 h5 */
	public String wapDomain;
	/** pc项目域名 */
	public String pcDomain;
	/** 后台项目域名 */
	public String adminDomain;
	/** 商家后台域名 */
	public String businessDomain;

	/** 阿里云短信的AccessKeyId */
	public String aliAccessKeyId;
	/** 阿里云短信的AccessKeySecret */
	public String aliAccessKeySecret;
	/** 阿里云短信的签名 */
	public String aliSign;
	/** 阿里云短信模板 */
	public String aliTemplateCode;

	/** 融云key */
	public String ryKey;
	/** 融云密钥 */
	public String rySecret;
	/** 极光推送key */
	public String jpushKey;
	/** 极光推送密钥 */
	private String jpushSecret;
	/** 聚合银行卡校验key*/
	private String jhBankKey;
	/** 地图ak */
	private String mapKey;

	/** 高德地图key */
	private String amapKey;

	/** 阿里云物流接口appCode*/
	private String aliExpAppCode;
	/** 阿里云物流接口appKey*/
	private String aliExpAppKey;
	/** 阿里云物流接口appSecret*/
	private String aliExpAppSecret;
	
	
	public static Properties properties = null;

	public static Properties getInstance() {
		if (properties == null) {
			synchronized (Properties.class) {
				if (properties == null) {
					properties = new Properties();
				}
			}
		}
		return properties;
	}
	
	public String getAccesskey() {
		return accesskey;
	}
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	public String getFromAlias() {
		return fromAlias;
	}
	public void setFromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}
	public String getQiniuAk() {
		return qiniuAk;
	}
	public void setQiniuAk(String qiniuAk) {
		this.qiniuAk = qiniuAk;
	}
	public String getQiniuSk() {
		return qiniuSk;
	}
	public void setQiniuSk(String qiniuSk) {
		this.qiniuSk = qiniuSk;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSmsAppkey() {
		return smsAppkey;
	}
	public void setSmsAppkey(String smsAppkey) {
		this.smsAppkey = smsAppkey;
	}
	public String getSmsValidmodelcode() {
		return smsValidmodelcode;
	}
	public void setSmsValidmodelcode(String smsValidmodelcode) {
		this.smsValidmodelcode = smsValidmodelcode;
	}
	public String getRsaFilepath() {
		return rsaFilepath;
	}
	public void setRsaFilepath(String rsaFilepath) {
		this.rsaFilepath = rsaFilepath;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getApiDomain() {
		return apiDomain;
	}

	public void setApiDomain(String apiDomain) {
		this.apiDomain = apiDomain;
	}

	public String getWapDomain() {
		return wapDomain;
	}

	public void setWapDomain(String wapDomain) {
		this.wapDomain = wapDomain;
	}

	public String getPcDomain() {
		return pcDomain;
	}

	public void setPcDomain(String pcDomain) {
		this.pcDomain = pcDomain;
	}

	public String getAdminDomain() {
		return adminDomain;
	}

	public void setAdminDomain(String adminDomain) {
		this.adminDomain = adminDomain;
	}

	public String getAliAccessKeyId() {
		return aliAccessKeyId;
	}

	public void setAliAccessKeyId(String aliAccessKeyId) {
		this.aliAccessKeyId = aliAccessKeyId;
	}

	public String getAliAccessKeySecret() {
		return aliAccessKeySecret;
	}

	public void setAliAccessKeySecret(String aliAccessKeySecret) {
		this.aliAccessKeySecret = aliAccessKeySecret;
	}

	public String getAliSign() {
		return aliSign;
	}

	public void setAliSign(String aliSign) {
		this.aliSign = aliSign;
	}

	public String getAliTemplateCode() {
		return aliTemplateCode;
	}

	public void setAliTemplateCode(String aliTemplateCode) {
		this.aliTemplateCode = aliTemplateCode;
	}

	public String getRyKey() {
		return ryKey;
	}

	public void setRyKey(String ryKey) {
		this.ryKey = ryKey;
	}

	public String getRySecret() {
		return rySecret;
	}

	public void setRySecret(String rySecret) {
		this.rySecret = rySecret;
	}

	public String getJpushKey() {
		return jpushKey;
	}

	public void setJpushKey(String jpushKey) {
		this.jpushKey = jpushKey;
	}

	public String getJpushSecret() {
		return jpushSecret;
	}

	public void setJpushSecret(String jpushSecret) {
		this.jpushSecret = jpushSecret;
	}

	public String getJhBankKey() {
		return jhBankKey;
	}

	public void setJhBankKey(String jhBankKey) {
		this.jhBankKey = jhBankKey;
	}

	public String getMapKey() {
		return mapKey;
	}

	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	public String getAliExpAppCode() {
		return aliExpAppCode;
	}

	public void setAliExpAppCode(String aliExpAppCode) {
		this.aliExpAppCode = aliExpAppCode;
	}

	public String getAliExpAppKey() {
		return aliExpAppKey;
	}

	public void setAliExpAppKey(String aliExpAppKey) {
		this.aliExpAppKey = aliExpAppKey;
	}

	public String getAliExpAppSecret() {
		return aliExpAppSecret;
	}

	public void setAliExpAppSecret(String aliExpAppSecret) {
		this.aliExpAppSecret = aliExpAppSecret;
	}

	public String getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(String businessDomain) {
		this.businessDomain = businessDomain;
	}

	public String getAmapKey() {
		return amapKey;
	}

	public void setAmapKey(String amapKey) {
		this.amapKey = amapKey;
	}
}
