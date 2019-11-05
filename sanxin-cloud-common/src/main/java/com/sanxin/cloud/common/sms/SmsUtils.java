package com.sanxin.cloud.common.sms;

import com.alibaba.fastjson.JSONObject;
/*import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.function.common.GetRest;
import com.function.common.RestResponse;*/
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.exception.ThrowJsonException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信工具类
 *
 * @author arno
 */
@Component
public class SmsUtils {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    public static SmsUtils smsUtils = null;

    public static SmsUtils getInstance() {
        if (smsUtils == null) {
            synchronized (SmsUtils.class) {
                if (smsUtils == null) {
                    smsUtils = new SmsUtils();
                }
            }
        }
        return smsUtils;
    }

}
