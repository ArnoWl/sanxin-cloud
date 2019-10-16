package com.sanxin.cloud.app.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.app.api.service.PayService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.service.system.login.LoginDto;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.service.system.pay.scb.SCBPayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author arno
 * @version 1.0
 * @date 2019-10-14
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private RedisUtilsService redisUtilsService;
    @Autowired
    private SCBPayService scbPayService;


    @Override
    public RestResult testPaySign(String token, BigDecimal money, Integer paytype) {
        //获取用户信息
        LoginDto loginDto=loginTokenService.validLoginLoginDto(token);
        PayTypeEnums typeEnums=PayTypeEnums.getEnums(paytype);
        String paycode= FunctionUtils.getOrderCode("T");
        switch (typeEnums) {
            case MONEY:
                //使用余额支付
                break;
            case PROMPT_PAY:
                break;
            case VISA_CARD:
                break;
            case MASTER_CARD:
                break;
            case GOOGLE_PAY:
                break;
            case APPLE_PAY:
                break;
            case ALI_PAY:
                break;
            case SCB_PAY:
                JSONObject obj=scbPayService.transactions(token,money,paycode);
                return RestResult.success("SUCCESS",obj);
            default:
                return RestResult.fail("Wrong payment method");
        }

        return null;
    }
}
