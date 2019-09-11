package com.sanxin.cloud.common.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

/**
 * 支付宝小程序获取用户信息工具类
 * @author xiaoky
 * @date 2019-09-09
 */
public class AliLoginUtil {

    private static String url = "https://openapi.alipay.com/gateway.do";
    private static String appId = "";
    private static String privateKey = "";
    private static String publicKey = "";

    /**
     * 支付宝小程序授权登录获取accessToken
     * @param authCode
     * @return
     * @throws AlipayApiException
     */
    public static AlipaySystemOauthTokenResponse getAccessToken(String authCode) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "GBK", publicKey, "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCode);
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        return response;
    }

    /**
     * 通过accessToken获取用户信息
     * @param accessToken
     * @return
     */
    public static AlipayUserInfoShareResponse getAliUserInfo(String accessToken) {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "GBK", publicKey, "RSA2");
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = null;
        try {
            response = alipayClient.execute(request, accessToken);
            if (response.isSuccess()) {
                return response;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }
}
