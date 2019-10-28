package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.RegistService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.sms.SMSSender;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CAccountMapper;
import com.sanxin.cloud.service.CCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册Service
 *
 * @author xiaoky
 * @date 2019-09-16
 */
@Service
public class RegistServiceImpl implements RegistService {
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private RedisUtilsService redisUtilsService;
    @Autowired
    private CAccountMapper accountMapper;

    /**
     * 发送验证码
     *
     * @param phone
     * @param region
     * @return
     */
    @Override
    public RestResult sendVerCode(String phone, String region) {

        RestResult result = SMSSender.sendSms(phone);
        if (!"".equals(result)) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }

    /**
     * 注册
     * @param customer
     * @return
     * @throws Exception
     */
    @Override
    public RestResult doRegister(CCustomer customer) {
        check(customer);
        CCustomer user = customerService.getOne(new QueryWrapper<CCustomer>().eq("phone", customer.getPhone()));
        if (user != null) {
            return RestResult.fail("phone_exist");
        }
        //密码校验格式
        boolean validPwd = FunctionUtils.validLoginPwd(customer.getPassWord());
        if (!validPwd) {
            return RestResult.fail("user_login_pass_error");
        }
        // 校验验证码
        RestResult result = SMSSender.validSms((customer.getAreaCode() + customer.getPhone()), customer.getVerCode());
        if (!result.status) {
            return result;
        }
        //加密密码
        String pass = PwdEncode.encodePwd(customer.getPassWord());
        customer.setPassWord(pass);
        customer.setNickName(customer.getPhone());
        customer.setHeadUrl(customer.getPhone());
        boolean save = customerService.save(customer);
        CAccount account = new CAccount();
        account.setCid(customer.getId());
        int insert = accountMapper.insert(account);
        if (insert > 0 && save) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 校验必填
     *
     * @param customer
     */
    private void check(CCustomer customer) {
        if (StringUtils.isEmpty(customer.getPhone())) {
            throw new ThrowJsonException("register_phone_empty");
        }
        if (StringUtils.isEmpty(customer.getPassWord())) {
            throw new ThrowJsonException("register_phone_empty");
        }
        if (StringUtils.isEmpty(customer.getVerCode())) {
            throw new ThrowJsonException("verifycode_not_exist");
        }
    }
}
