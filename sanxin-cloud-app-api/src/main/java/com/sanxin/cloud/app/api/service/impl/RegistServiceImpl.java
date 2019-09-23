package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.RegistService;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.CCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册Service
 * @author xiaoky
 * @date 2019-09-16
 */
@Service
public class RegistServiceImpl implements RegistService {
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private RedisUtilsService redisUtilsService;

    @Override
    public RestResult sendVerCode(String phone, String region) {
        /*CCustomer customer = this.baseMapper.selectOne(new QueryWrapper<CCustomer>().eq("phone", phone));
        if (customer == null) {
            throw new ThrowJsonException("用户不存在");
        }*/
        return null;
    }

    @Override
    public RestResult doRegister(CCustomer customer) throws Exception {
        check(customer);
        CCustomer user = customerService.getOne(new QueryWrapper<CCustomer>().eq("phone", customer.getPhone()));
        if (user != null) {
            return RestResult.fail("不能重复注册");
        }
        // 根据手机号获取验证码
        /*String verCode = redisUtilsService.getKey(Constant.PHONE_VERCODE + customer.getPhone());
        if (!customer.getVerCode().equals(verCode)) {
            throw new ThrowJsonException("验证码不匹配");
        }*/
        //加密密码
        String pass = PwdEncode.encodePwd(customer.getPassWord());
        customer.setPassWord(pass);
        customerService.save(customer);

        return RestResult.success("success");
    }

    /**
     * 校验必填
     * @param customer
     */
    private void check(CCustomer customer) throws Exception {
        if (StringUtils.isEmpty(customer.getPhone())) {
            throw new ThrowJsonException("手机号不能为空");
        }
        if (StringUtils.isEmpty(customer.getPassWord())) {
            throw new ThrowJsonException("密码不能为空");
        }
        if (StringUtils.isEmpty(customer.getVerCode())) {
            throw new ThrowJsonException("验证码不能为空");
        }
    }
}
