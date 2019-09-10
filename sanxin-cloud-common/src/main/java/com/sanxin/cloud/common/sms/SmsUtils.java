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

    /**
     * 聚合发送短信
     *
     * @param phone
     * @param code
     * @return
     */
    /*public RestResult mobileQuery(String phone, String code) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";// 请求接口地址
        Map<String, String> params = new HashMap<String, String>();// 请求参数
        params.put("mobile", phone);// 接受短信的用户手机号码
        params.put("tpl_id", Config.properties.getSmsValidmodelcode());// 您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "#code#=" + code);// 您设置的模板变量，根据实际情况修改
        params.put("key", Config.properties.getSmsAppkey());// 应用APPKEY(应用详细页查询)
        try {
            result = HttpUtil.getInstance().get(url, params);
            JSONObject object = JSONObject.parseObject(result);
            if (object.getIntValue("error_code") == 0) {
                JSONObject object2 = object.getJSONObject("result");
                return RestResult.success("发送成功", object2.getString("sid"));
            } else {
                return RestResult.fail(object.getString("reason"));
            }
        } catch (Exception e) {
            return RestResult.fail("发送失败");
        }
    }*/

    /**
     * https://www.kewail.com/ 发送短信
     *
     * @param phoneNumber
     * @param validcode
     * @return
     */
    /*public RestResult sendSms(String phoneNumber, String validcode) {
        try {
            // type:0普通短信 1营销短信
            int type = 0;
            // 国家区号
            String nationcode = "86";
            // 短信模板的变量值 ，将短信模板中的变量{0},{1}替换为参数中的值，如果短信模板中没有变量，则这个值填null
            List<String> params = new ArrayList<String>();
            // 模板中存在多个可变参数，可以添加对应的值。
            params.add(validcode);
            // 自定义字段，用户可以根据自己的需要来使用
            String ext = "";
            // 初始化单发
            SmsSingleSender singleSender = new SmsSingleSender(Config.properties.getAccesskey(),
                    Config.properties.getSecretkey());
            // 普通单发,注意前面必须为【】符号包含，置于头或者尾部。
            SmsSingleSenderResult singleSenderResult = singleSender.send(type, nationcode, phoneNumber,
                    Config.properties.getSignId(), Config.properties.getTemplateId(), params, ext);
            if (singleSenderResult.result != 0) {
                return RestResult.fail(singleSenderResult.errMsg);
            }
            return RestResult.success("发送成功");
        } catch (Exception e) {
            throw new ThrowJsonException("发送短信失败");
        }
    }*/


    /**
     * 阿里云发送短信
     *
     * @param phone 手机号
     * @param code 验证码
     * @param templateCode 模板code
     * @return
     */
    /*public RestResult aliyunSendSms(String phone, String code, String templateCode) {
        DefaultProfile profile = DefaultProfile.getProfile("default", Config.properties.getAliAccessKeyId(), Config.properties.getAliAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", Config.properties.getAliSign());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{'code':'" + code + "'}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (Exception e) {
            throw new ThrowJsonException("发送短信失败");
        }
        return RestResult.success("发送成功");

    }*/


}
