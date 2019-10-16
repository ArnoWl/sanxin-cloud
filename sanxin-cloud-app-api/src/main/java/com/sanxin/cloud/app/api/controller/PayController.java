package com.sanxin.cloud.app.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.app.api.service.PayService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.enums.LoginChannelEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.system.login.LoginDto;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.service.system.pay.scb.SCBPayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author arno
 * @version 1.0
 * @date 2019-10-14
 */
@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private PayService payService;
    @Autowired
    private SCBPayService scbPayService;
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private RedisUtilsService redisUtilsService;

    /**
     * 授权
     * @return 返回APP授权地址
     */
    @GetMapping(value = "/validAuthorize")
    public RestResult validAuthorize(){
        String token = BaseUtil.getUserToken();
        //获取用户信息
        LoginDto loginDto=loginTokenService.validLoginLoginDto(token);
        //获取token如果不存在的情况下就重新获取
        String key=loginDto.getTid()+"_scbtoken";
        String accessToken=redisUtilsService.getKey(key);
        if(StringUtils.isEmpty(accessToken) || "null".equals(accessToken)){
            return RestResult.fail("Please go to authorization");
        }else{
            return RestResult.success("Success");
        }
    }

    /**
     * 授权
     * @return 返回APP授权地址
     */
    @GetMapping(value = "/authorize")
    public RestResult authorize(){
        String token = BaseUtil.getUserToken();
        String callbak_url=scbPayService.authorize(token, LoginChannelEnums.APP);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("callbackUrl",callbak_url);
        return RestResult.success("Success",jsonObject);
    }

    /**
     * 测试支付签名
     * @return
     */
    @GetMapping(value = "/testPaySign")
    public RestResult handlePay(String authcode, BigDecimal money, Integer paytype){
        String token = BaseUtil.getUserToken();
        String scbToken=scbPayService.getToken(token,authcode);
        if(StringUtils.isEmpty(scbToken)){
           return  RestResult.fail("1011","Authorization failed, please re authorize","","");
        }
        RestResult result=payService.testPaySign(token,money,paytype);
        return result;
    }

}
