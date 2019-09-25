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

    // TODO 测试地址，上线后需替换
    // https://openapi.alipay.com/gateway.do
    private static String url = "https://openapi.alipaydev.com/gateway.do";
    private static String appId = "2017052600960970";
    private static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9WQE2ZDbIT1ZaZsK6tWytI+Fp9i+vgxIwEWaNl3AHRIqeDyM5k+KCkOfMz+alU3EpOkaGuz9Pz7eCTkC/RhoWLwpylBrUDyvRyRA7IKlTVyVSH11k3EPwluz0rcfRIx9NyKoZgBWgXREZqcvPzz9WvIBRRHM5f4NokHs31UzMInTGW0Pza5Hyh556mJOQ7+Grjo1xj0G7bPYSLqZBxHKVXsrclEp5mYUSFemI+bd1ImjQ9cIf3u6h4B1sCEptzhff8wKWmoBLjhrGAAGUVTMdv0TspsXgGeAnQFlklm8SrLy+v4BSJh2P6cpzrJkjVOFBIIOAGtulAO0d0hkw7QOhAgMBAAECggEAIMTcjts9F+GTIkYSgVmQm05QXpQ6oHdgmG0KgIqq2rcj/kLEZ9yOIOaPF0ULt6T/OpFZT0vsoxVhvu1oCzJIpXoypq23DugpPz86zeDTLRcx4EqJUUFFiMe17op8wFcBveZyecNO+tfgc76NBUPqxEoPMwFTp6nHxjrq+Diz98faBpx38ex/ZhcBflAKQ4WH4JUIhL3h4ympnAD1gG2q8Jbou6igBFV6xaw0X7ZwannBLhxU99IYbXXL/hfodSOn1COi9UWHNKxPIzzv7WW2NX2og9rcZtKP5ngzWohLLmWnPN8a/tDG0OXeGPqo+QSt+wkLM65MWKju7nYY53+ygQKBgQD4bZvI5CUEzFxyiPqkqWIXAjXZ9shDw99UxBvglkGUnGBDgql0+3u/XxvDKzUak9NlOXXwF6mJhmHjJN2zjgAI0mT2hCYMaBMD9wh10H9pTgMfuGteqjjZCw9U3ChGqbyutrsDKN+wJT3b4LcpLitv4YVfysq3rM73Q+KQDIClOQKBgQDDHmnVeFsmwP8TzunNx7vCfP8dV7FmZuIp3e2KjD8fjt5ytWOvr+tS4IxoovPIgwDWAGAEIt0L6rwVoA2YhVaARgoeG74/vr8IMVRga9EDHVZCgxvu9Z7JyLjDd4KRh6kZk2h7OM8gJBlsgEC8Na115OMXJHa8e4eDeat52Sh5qQKBgQCwTalXQoH7C8U4C3pFTWgtiAYLr5WkceeW0q4uDrRv8SJoBtyYBrllsOuU8J75Q5uspMDMKR7KWLIzb0BfYxEnMZkxUctm1p1YHQLGCbfCVfjllA2u2wMJFU5GHRAARgl/vHKv5VwKOAPUfX+/G+L7vYLX3qVLALrIxEaPkC/kEQKBgQCkJAWsSx+DLoBmzX3qWsgdCMIkkQzvtEU2KzgVgX3osmt33BgMR9WTVstHiy3B1FLztEURtbJ2v/WRy58kW6c1KbaBNZ2KSpCx962fn5OpJxjVwy3QjFgUMAMejH8Opi0fEt6mKlg0Fao9mHbc3dsafn57jXVFgjWx6LzUOFbQYQKBgEUYuOi5WMTX/DNXcvpRIhcPmEwXRq4DbNZv6PN3/L7F7vIgSZC+ereU/QVVwq6KY8ic1WYt/PouWGot/17ecgV0lvY6e6NAlwzWxOvlI5UpwAq5ZxV5MtgDh9n70FBnTaf8Oj4gKCzgGmLuQUeCc/HitKW/X31lMhwe7gc2MtbG";
    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArcvIpmG1Vq7VXdUJS8wffeI7WXlKtRegNl5k3iGjB+lF4uoic+pOn/6muMET4VLsZozCyp+w+Um3N4JRgRO8/Rb3AxG7WkddrKzTux90tKf5hnw5fOQ6eNV6VOiM6ebBhv6Li/yKlGdnXhJnjtJgNvyp2npnq/FqiqyqGYHuX3VFzMEyQTe/ItIpIR/PjuWCFYx7jib0j76HVEDib1txq+wBjILjYP6oC1XQUpzfWN34REhjk3rkQjar07iZxS2zeMdOCrdD6ZsMwr0xCCZCHsSCGlfd/Yx+fo3Ir59zJ9awL2DVDUGbkPlXF/ra/vuV3z1cnpqRYxNJLxFEHE2aJwIDAQAB";

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
