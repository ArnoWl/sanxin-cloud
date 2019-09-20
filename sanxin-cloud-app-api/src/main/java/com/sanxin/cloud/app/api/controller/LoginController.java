package com.sanxin.cloud.app.api.controller;


import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.app.api.service.LoginService;
import com.sanxin.cloud.app.api.service.RegistService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.properties.PropertiesUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.dto.LoginRegisterVo;
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
    private LoginService loginService;
    @Autowired
    private RegistService registService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LoginTokenService loginTokenService;

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
        return registService.sendVerCode(phone,region);
    }


    /**
     * 注册用户
     * @param customer
     * @return
     * @throws Exception
     */
    @PostMapping(value = MappingUtils.REGISTER)
    public RestResult doRegister(@RequestBody CCustomer customer) throws Exception {
        registService.doRegister(customer);
        return RestResult.success("注册成功");
    }


    /**
     * 登录
     * @param loginRegisterVo 登录信息
     * @return
     */
    @PostMapping(value = MappingUtils.LOGIN)
    public RestResult doLogin(LoginRegisterVo loginRegisterVo) {
        return loginService.doLogin(loginRegisterVo);
    }


    /**
     * 个人资料
     * @return
     */
    @PostMapping(value = MappingUtils.PERSONAL_INFORM)
    public RestResult queryPersonalInform() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return loginService.personalInform(cid);
    }

    /**
     * 修改个人资料
     * @return
     */
    @PostMapping(value = MappingUtils.PERSONAL_INFORM)
    public RestResult updatePersonalInform(CCustomer customer) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        if (cid != null) {
            customer.setId(cid);
        }
        return loginService.updatePersonalInform(customer);
    }

    /**
     * 修改登录或支付密码
     * @param Phone 手机号
     * @param verCode 验证码
     * @param password 密码
     * @param type 1登录密码 2支付密码
     * @return
     */
    @PostMapping(value = MappingUtils.PERSONAL_INFORM)
    public RestResult updatePassword(String Phone,String verCode,String password,Integer type) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return loginService.updateLoginPass(Phone,verCode,password,cid,type);
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

