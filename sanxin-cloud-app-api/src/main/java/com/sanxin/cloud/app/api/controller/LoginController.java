package com.sanxin.cloud.app.api.controller;


import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.app.api.service.LoginService;
import com.sanxin.cloud.app.api.service.RegistService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.properties.PropertiesUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.dto.CustomerHomeVo;
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
    @RequestMapping(value = MappingUtils.SEND_REGISTER_CODE)
    public RestResult sendVerCode(String phone,String region) throws Exception {
        return registService.sendVerCode(phone,region);
    }

    /**
     * 注册用户
     * @param customer
     * @return
     * @throws Exception
     */
    @RequestMapping(value = MappingUtils.REGISTER ,method = RequestMethod.POST)
    public RestResult doRegister(CCustomer customer) throws Exception {
        return registService.doRegister(customer);
    }

    /**
     * 登录
     * @param loginRegisterVo 登录信息
     * @return
     */
    @RequestMapping(value = MappingUtils.LOGIN)
    public RestResult doLogin(LoginRegisterVo loginRegisterVo) {
        return loginService.doLogin(loginRegisterVo);
    }

    /**
     * 个人资料
     * @return
     */
    @RequestMapping(value = MappingUtils.PERSONAL_INFORM)
    public RestResult queryPersonalInform() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        CustomerHomeVo homeVo = loginService.personalInform(cid);
        return RestResult.success(homeVo);
    }

    /**
     * 修改个人资料
     * @return
     */
    @RequestMapping(value = MappingUtils.UPDATE_PERSONAL_INFORM)
    public RestResult updatePersonalInform(CCustomer customer) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        if (cid != null) {
            customer.setId(cid);
        }
        return loginService.updatePersonalInform(customer);
    }

    /**
     * 修改登录或支付密码
     * @param verCode 验证码
     * @param password 密码
     * @param type 1登录密码 2支付密码
     * @return
     */
    @RequestMapping(value = MappingUtils.UPDATE_PASSWORD)
    public RestResult updatePassword(String verCode,String password,Integer type,Integer userType) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return loginService.updateLoginPass(verCode,password,cid,type,userType);
    }

    /**
     * 找回密码
     * @param phone
     * @param password
     * @param verCode
     * @return
     */
    @RequestMapping(value = MappingUtils.FORGET_PASSWORD)
    public RestResult forgetPassword(String phone,String password,String verCode) {
        return loginService.forgetPassword(phone,password,verCode);
    }

}

