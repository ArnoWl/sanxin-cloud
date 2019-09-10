package com.sanxin.cloud.common.sms;


import com.sanxin.cloud.common.properties.PropertiesUtil;
import com.sanxin.cloud.common.radoms.RandNumUtils;
import com.sanxin.cloud.common.regular.RpxUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.config.redis.SpringBeanFactoryUtils;
import com.sanxin.cloud.enums.RandNumType;
import org.apache.commons.lang3.StringUtils;

/**
 * 发送短信
 *
 * @author arno
 */
public class SendSms {

    public static SendSms sendSms = null;

    public static SendSms getInstance() {
        if (sendSms == null) {
            synchronized (SendSms.class) {
                if (sendSms == null) {
                    sendSms = new SendSms();
                }
            }
        }
        return sendSms;
    }

    /**
     * 聚合发送短信
     *
     * @param phone
     * @return
     */
    public RestResult sendSms_jvhe(String phone) {
        String validphone = RpxUtils.getInstance().valid_phone(phone);
        if (!StringUtils.isBlank(validphone)) {
            return RestResult.fail(validphone);
        }
        String validcode = RandNumUtils.getInstance().get(RandNumType.NUMBER, 6);
        //防止重复点击发送验证码
        String send_flag = "sendflag_sms_" + phone;
        boolean flag = getRedisUtilsService().setIncrSecond(send_flag, 60);
        if (!flag) {
            return RestResult.fail("您已发送验证码,请1分钟后重试");
        }
        /*RestResult result = SmsUtils.getInstance().mobileQuery(phone, validcode);
        if (result.isStatus()) {
            //保存验证码到缓存
            String key = "send_sms_" + phone;
            getRedisUtilsService().setKey(key, validcode, 600);
        }*/
        return null;
    }

    /**
     * kewail发送短信
     *
     * @param phone
     * @return
     */
    public RestResult sendSms_kewail(String phone) {
        String validphone = RpxUtils.getInstance().valid_phone(phone);
        if (!StringUtils.isBlank(validphone)) {
            return RestResult.fail(validphone);
        }
        String validcode = RandNumUtils.getInstance().get(RandNumType.NUMBER, 6);
        //防止重复点击发送验证码
        String send_flag = "sendflag_sms_" + phone;
        boolean flag = getRedisUtilsService().setIncrSecond(send_flag, 60);
        if (!flag) {
            return RestResult.fail("您已发送验证码,请1分钟后重试");
        }
        /*RestResult result = SmsUtils.getInstance().sendSms(phone, validcode);
        if (result.isStatus()) {
            //保存验证码到缓存
            String key = "send_sms_" + phone;
            getRedisUtilsService().setKey(key, validcode, 600);
        }*/
        return null;
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param templateCode
     * @return
     */
    public RestResult sendSms_aliyun(String phone, String templateCode) {
        String validphone = RpxUtils.getInstance().valid_phone(phone);
        if (!StringUtils.isBlank(validphone)) {
            return RestResult.fail(validphone);
        }
        String validcode = RandNumUtils.getInstance().get(RandNumType.NUMBER, 6);
        //防止重复点击发送验证码
        String send_flag = "sendflag_sms_" + phone;
        boolean flag = getRedisUtilsService().setIncrSecond(send_flag, 60);
        if (!flag) {
            return RestResult.fail("您已发送验证码,请1分钟后重试");
        }
        /*RestResult result = SmsUtils.getInstance().aliyunSendSms(phone, validcode, templateCode);
        if (result.isStatus()) {
            //保存验证码到缓存
            String key = "send_sms_" + phone;
            getRedisUtilsService().setKey(key, validcode, 600);
        }*/
        return null;
    }

    /**
     * 校验验证码
     *
     * @param phone
     * @param validcode
     * @return
     */
    public RestResult validSms(String phone, String validcode,String ext) {
        String validphone = RpxUtils.getInstance().valid_phone(phone);
        if (!StringUtils.isBlank(validphone)) {
            return RestResult.fail(validphone);
        }
        if (StringUtils.isEmpty(validcode)) {
            return RestResult.fail(PropertiesUtil.getVal("verifycode_not_exist",ext));
        }
        //保存验证码到缓存
        String key = "send_sms_" + phone;
        String val = getRedisUtilsService().getKey(key);
        if (StringUtils.isEmpty(val)) {
            return RestResult.fail(PropertiesUtil.getVal("login_pwd_fail",ext));
        }
        if (!val.equals(validcode)) {
            return RestResult.fail(PropertiesUtil.getVal("login_pwd_fail",ext));
        }
        //校验成功后删除
        getRedisUtilsService().deleteKey(key);
        return RestResult.success(PropertiesUtil.getVal("success",ext));
    }

    /**
     * 注入redis
     *
     * @return
     */
    private RedisUtilsService getRedisUtilsService() {
        return SpringBeanFactoryUtils.getApplicationContext().getBean(RedisUtilsService.class);
    }
}
