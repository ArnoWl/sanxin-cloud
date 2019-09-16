package com.sanxin.cloud.app.api.controller;


import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.properties.PropertiesUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.service.CCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Janice
 * @date 2019-08-06
 */
@RestController
@RequestMapping("/register")
public class LoginController {
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private HttpServletRequest request;


    /**
     * 发送手机验证码
     *
     * @param phone
     * @param region 1、注册。2、修改密码
     * @return
     * @throws Exception
     */
    @GetMapping(value = MappingUtils.SEND_REGISTER_CODE)
    public RestResult sendVerCode(@RequestParam String phone,String region) throws Exception {
        return customerService.sendVerCode(phone,region);
    }


    /**
     * 注册用户
     * @param customer
     * @return
     * @throws Exception
     */
    @PostMapping(value = MappingUtils.REGISTER)
    public RestResult doRegister(@RequestBody CCustomer customer) throws Exception {
        customerService.doRegister(customer);
        return RestResult.success("注册成功");
    }


    /**
     * 登录
     * @param phone
     * @param passWord
     * @return
     */
    @PostMapping(value = MappingUtils.LOGIN)
    public RestResult doLogin(String phone,String passWord) {
        String ext = BaseUtil.getLanguage();
        return customerService.doLogin(phone,passWord,ext);
    }

    /**
     * 忘记密码
     * @param loginVo
     * @return
     */
    /*@RequestMapping(value = MappingUtils.SEND_FORGET_CODE)
    public RestResult sendResetPasswordCode(LoginRegisterVo loginVo) {
        String ext = LanguageUtils.getLanguage(request);
        if (loginVo.getType() == null) {
            return RestResult.fail(PropertiesUtil.getVal("type_choose",ext));
        }
        if (StringUtils.isBlank(loginVo.getLogin())) {
            return RestResult.fail(PropertiesUtil.getVal("login_not_exist",ext));
        }
        return loginService.sendResetPasswordCode(loginVo,ext);
    }*/

    /**
     * 重置密码
     * @param loginRegisterVo
     * @return
     */
    /*@RequestMapping(value = MappingUtils.FORGET_PASSWORD)
    public RestResult forgetPassword(LoginRegisterVo loginRegisterVo) {
        String ext = LanguageUtils.getLanguage(request);
        if (loginRegisterVo.getType() == null) {
            return RestResult.fail(PropertiesUtil.getVal("type_choose",ext));
        }
        if (StringUtils.isBlank(loginRegisterVo.getLogin())) {
            return RestResult.fail(PropertiesUtil.getVal("login_not_exist",ext));
        }
        if (StringUtils.isBlank(loginRegisterVo.getValidCode())) {
            return RestResult.fail(PropertiesUtil.getVal("verifycode_not_exist",ext));
        }
        if (StringUtils.isBlank(loginRegisterVo.getPassword())) {
            return RestResult.fail(PropertiesUtil.getVal("password_empty",ext));
        }
        if (StringUtils.isBlank(loginRegisterVo.getConfirmPassword())) {
            return RestResult.fail(PropertiesUtil.getVal("confirmpd_empty",ext));
        }
        return loginService.handleResetPassword(loginRegisterVo,ext);
    }*/


}

