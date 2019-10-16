package com.sanxin.cloud.app.api.service;

import com.sanxin.cloud.common.rest.RestResult;

import java.math.BigDecimal;

/**
 * @author arno
 * @version 1.0
 * @date 2019-10-14
 */
public interface PayService {

    public RestResult testPaySign(String token, BigDecimal money, Integer paytype);
}
