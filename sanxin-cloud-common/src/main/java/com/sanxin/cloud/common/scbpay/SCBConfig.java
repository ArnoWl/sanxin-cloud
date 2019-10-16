package com.sanxin.cloud.common.scbpay;

/**
 * @author arno
 * @version 1.0
 * @date 2019-10-14
 */
public class SCBConfig {

    /**
     * 请求网关
     */
    public static final String url="https://api.partners.scb/partners/sandbox";

    /**
     * key
     */
    public static final String apikey="l7c1634f7f8d4f45c889c308349b7c2c55";

    /**
     * secert
     */
    public static final String apisecret="29a0251f83744020919fd2aa6aabefd0";

    /**
     * appNAME
     */
    public static final String app_name="powerplusapp";

    /**
     * 账单id
     */
    public static final String account_id ="952839489658767";

    /**
     * 商户号
     */
    public static final String merchant_id ="147645385517229";


    /**
     * 机器id
     */
    public  static final String terminal_id="398764234183322";


    /**********method**********/
    public static final String authorize="/v2/oauth/authorize";//权限认证

    public static final String getToken="/v1/oauth/token";//获取token

    public static final String refresh="/v1/oauth/token/refresh";//查询token

    public static final String transactions="/v3/deeplink/transactions";//发起交易

    public static final String getTransactions="/v2/transactions/";//获取交易 后面需要带单号



}
