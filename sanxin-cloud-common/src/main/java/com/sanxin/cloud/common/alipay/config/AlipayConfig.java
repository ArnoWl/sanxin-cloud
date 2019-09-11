package com.sanxin.cloud.common.alipay.config;

import com.sanxin.cloud.common.alipay.util.UtilDate;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
	/*public static String partner = "2088221648158252";
	// 商户的私钥
	public static String private_key = "7wjg5xvg0xuydm3nv0q3bso6i2oqblgw";

	public static String partner_account="3022572135@qq.com";

	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	//app支付私钥
	public static String ali_private_key= "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALkVRvZlUq5uwY/9Nlzmq5f94TSWuAggxygyjJavwg7d1Y9fm6W0Zg/WU8HbjadpzuweGOhGTwbG8JySir5tAEpFitRgqN475PQkxD/aJMWZQ5xqV3wHmazqF8/jLzmpls+7ATWkQ5cehAODKYuid+USmJ6jCA0cnQLncFlQVlOHAgMBAAECgYEAhYyF1NsNyxHeF4D1jhlf7d9idijfB/Zm3mmDKmQOGd4V8EtjqzC9iGX1r+c1knx1s8iP3mKFOXWpPsE63ecXEkQv7Q6IOWm+hta0t8zGcWRmJ0JQ59b3Y/ST3tYib2SV9yqiKpZJhxn4ijCyawZ9TsgAj91DU/kp4lp9v9PWr1ECQQD1bWCf+3XUInYq+W4J5sPUislKUoOqhAPSpYNiMuESs9ViHRmny+6udA5NeyZjsq4V7/qICS1HSRMmVlwviHSZAkEAwQ5pfdXJnxo8mGvx5CxpfKjeFFo16+Rdi8t5OZR1LZd6FNjBh3sIrTclPLcsHIRodwCiFO0Oos3juTJ9GF39HwJBAMwDRgT29PWISA5lFXGpoDP3wmfDu6ts3fimmVZx0OLJFqHeassvHJNr6c/ChSDvEQMvX2Thq//L4N1HgI4KslECQDLh0on449gMOTZPFQT3c6IIe+fPUvDmUoV6zmuPpkaq6uE9s9w6YDIPG8Bh4r1tZH8g6pcrV7UvILr6BKB8fS8CQQCE6PUUZo8BPQhc90fwnTNox6KFk18glSnAq4ENnLA0asyRBQjyOjy8BaJfUDGYp046rWsMCpvvuA71axFS4nXA";
	*/


    /**
     * 调试用，创建TXT日志文件夹路径
     */
    public static String LOG_PATH = "/logs/alipay/";


    /**
     * 字符编码格式 目前支持 gbk 或 utf-8
     */
    public static String INPUT_CHARSET = "utf-8";

    /**
     * 返回格式
     */
    public static String FORMAT = "json";

    /**
     * 签名方式 不需修改
     */
    public static String SIGN_TYPE = "RSA";

    public static String REFUND_SIGN_TYPE = "RSA2";

    /**
     * 支付宝网页端调用的接口名，无需修改
     */
    public static String WEB_SERVICE = "create_direct_pay_by_user";

    /**
     * 支付宝手机端端调用的接口名，无需修改
     */
    public static String APP_SERVICE = "mobile.securitypay.pay";
    public static String REFUND_SERVICE = "refund_fastpay_by_platform_pwd";

    public static String MOBILE_SERVICE = "alipay.trade.wap.pay";

    /**
     * 退款日期 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String REFUND_DATE = UtilDate.getDateFormatter();

    /**
     * 支付类型 ，无需修改
     */
    public static String PAYMENT_TYPE = "1";

}
