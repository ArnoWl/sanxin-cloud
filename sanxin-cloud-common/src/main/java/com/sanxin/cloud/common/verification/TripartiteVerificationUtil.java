package com.sanxin.cloud.common.verification;

import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.common.http.HttpUtil;
import com.sanxin.cloud.dto.VerificationVO;

public class TripartiteVerificationUtil {

    private static final String FACEBOOK_URL = "https://graph.facebook.com/me?fields=id,name,email&access_token=";
    private static final String GOOGLE_URL = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=";

    public static VerificationVO verification(String accessToken, Integer type) {
        VerificationVO vo = new VerificationVO();
        switch (type) {
            case 1:
                String facebook = null;
                try {
                    facebook = HttpUtil.getInstance().get(FACEBOOK_URL + accessToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JSONObject facebookJson = JSONObject.parseObject(facebook);
                String facebookId = facebookJson.getString("id");
                String facebookName = facebookJson.getString("name");
                String facebookEmail = facebookJson.getString("email");
                if (facebookName == null && facebookId == null) {
                    return null;
                }
                vo.setId(facebookId);
                vo.setName(facebookName);
                vo.setEmail(facebookEmail);
                return vo;
            case 2:
                String google = null;
                try {
                    google = HttpUtil.getInstance().get(GOOGLE_URL + accessToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JSONObject googleJson = JSONObject.parseObject(google);
                String googleId = googleJson.getString("sub");
                String googleName = googleJson.getString("name");
                String googleEmail = googleJson.getString("email");
                String googlePicture = googleJson.getString("picture");
                if (googleName == null && googleId == null) {
                    return null;
                }
                vo.setId(googleId);
                vo.setName(googleName);
                vo.setEmail(googleEmail);
                vo.setPicture(googlePicture);
                return vo;
        }
        return null;
    }

}
