package com.sanxin.cloud.app.api.controller;


import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.app.api.service.LoginService;
import com.sanxin.cloud.app.api.service.RegistService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.dto.ProgramBindVo;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.dto.CustomerHomeVo;
import com.sanxin.cloud.dto.LoginRegisterVo;
import com.sanxin.cloud.entity.CCustomer;
import org.apache.commons.lang3.StringUtils;
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
     * @param areaCode 1、注册。2、修改密码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = MappingUtils.SEND_REGISTER_CODE)
    public RestResult sendVerCode(String phone, String areaCode) throws Exception {
        return registService.sendVerCode(phone, areaCode);
    }

    /**
     * 注册用户
     *
     * @param customer
     * @return
     * @throws Exception
     */
    @RequestMapping(value = MappingUtils.REGISTER, method = RequestMethod.POST)
    public RestResult doRegister(CCustomer customer) throws Exception {
        return registService.doRegister(customer);
    }


    /**
     * 第三方登录(用户)
     * @param accessToken
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = MappingUtils.TRIPARTITE_LOGIN)
    public RestResult tripartiteLogin(String accessToken, String id, Integer type) throws Exception {
        return loginService.tripartiteLogin(accessToken, id, type);
    }

    /**
     * 第三方绑定(用户)
     * @param accessToken 第三方token
     * @param id 第三方id
     * @param type 1 facebook 登录  2 google登录
     * @param passWord 密码
     * @param phone 手机号
     * @param verCode 验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = MappingUtils.BINDING_PHONE)
    public RestResult bindingPhone(String accessToken, String id, Integer type,String passWord,String phone ,String verCode,String areaCode,String picture) throws Exception {
        return loginService.bindingPhone(accessToken, id, type,passWord,phone,verCode,areaCode,picture);
    }

    /**
     * 登录
     *
     * @param loginRegisterVo 登录信息
     * @return
     */
    @RequestMapping(value = MappingUtils.LOGIN)
    public RestResult doLogin(LoginRegisterVo loginRegisterVo) {
        return loginService.doLogin(loginRegisterVo);
    }

    /**
     * 处理小程序绑定手机号
     * @param vo
     * @return
     */
    @RequestMapping(value = MappingUtils.HANDLE_PROGRAM_BIND_PHONE)
    public RestResult handleProgramBindPhone(ProgramBindVo vo) {
        if (vo == null || StringUtils.isEmpty(vo.getUserId())) {
            return RestResult.fail("绑定失败");
        }
        if (StringUtils.isEmpty(vo.getPhone())) {
            return RestResult.fail("register_phone_empty");
        }
        if (StringUtils.isEmpty(vo.getVerCode())) {
            throw new ThrowJsonException("verifycode_not_exist");
        }
        if (StringUtils.isEmpty(vo.getAreaCode())) {
            throw new ThrowJsonException("areaCode_not_exist");
        }
        return registService.handleProgramBindPhone(vo);
    }

    /**
     * 个人资料
     *
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
     *
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
     * 修改个人资料
     *
     * @return
     */
    @RequestMapping(value = MappingUtils.UPDATE_EMAIL)
    public RestResult updateEmail(String email) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return loginService.updateEmail(cid,email);
    }

    /**
     * 修改登录或支付密码
     *
     * @param verCode  验证码
     * @param password 密码
     * @param type     1登录密码 2支付密码
     * @return
     */
    @RequestMapping(value = MappingUtils.UPDATE_PASSWORD)
    public RestResult updatePassword(String verCode, String password, Integer type, Integer userType) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return loginService.updateLoginPass(verCode, password, cid, type, userType);
    }

    /**
     * 找回密码
     *
     * @param phone
     * @param password
     * @param verCode
     * @return
     */
    @RequestMapping(value = MappingUtils.FORGET_PASSWORD)
    public RestResult forgetPassword(String phone, String password, String verCode) {
        return loginService.forgetPassword(phone, password, verCode);
    }

}

