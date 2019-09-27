package com.sanxin.cloud.app.api.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.app.api.service.LoginService;
import com.sanxin.cloud.common.Constant;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.alipay.AliLoginUtil;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginDto;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.dto.BusinessHomeVo;
import com.sanxin.cloud.dto.CustomerHomeVo;
import com.sanxin.cloud.dto.LoginRegisterVo;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.enums.LoginChannelEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.CCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xiaoky
 * @date 2019-09-16
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Value("${spring.redis.token.time}")
    private long redisTokenTime;
    @Autowired
    private RedisUtilsService redisUtilsService;
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private CCustomerMapper customerMapper;
    @Autowired
    private BBusinessService bbusinessService;
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private BusinessService businessService;

    /**
     * 登录
     *
     * @param loginRegisterVo 登录信息
     * @return
     */
    @Override
    public RestResult doLogin(LoginRegisterVo loginRegisterVo) {
        String phone = loginRegisterVo.getPhone();
        String passWord = loginRegisterVo.getPassWord();
        if (loginRegisterVo.getType() == null) {
            return RestResult.fail("data_exception");
        }
        // 如果不是小程序和用户端
        if (!(FunctionUtils.isEquals(StaticUtils.LOGIN_CUSTOMER, loginRegisterVo.getType())
                && FunctionUtils.isEquals(LoginChannelEnums.ALI_PROGRAM.getChannel(), loginRegisterVo.getChannel()))) {
            if (StringUtils.isEmpty(phone)) {
                throw new ThrowJsonException("register_phone_empty");
            }
            if (StringUtils.isEmpty(passWord)) {
                throw new ThrowJsonException("register_pass_empty");
            }
        }

        LoginDto loginDto = LoginDto.getInstance();
        // 判断登录类型
        // 用户
        if (FunctionUtils.isEquals(StaticUtils.LOGIN_CUSTOMER, loginRegisterVo.getType())) {
            // 判断渠道是否小程序
            // 小程序应该授权登录
            RestResult result = null;
            if (FunctionUtils.isEquals(loginRegisterVo.getChannel(), LoginChannelEnums.ALI_PROGRAM.getChannel())) {
                result = aliProgramLogin(loginRegisterVo);
            } else {
                result = appCustomerLogin(loginRegisterVo, loginDto, passWord, phone);
            }
            return result;
        } else if (FunctionUtils.isEquals(StaticUtils.LOGIN_BUSINESS, loginRegisterVo.getType())) {
            // 加盟商
            BBusiness business = bbusinessService.getOne(new QueryWrapper<BBusiness>().eq("phone", phone));
            if (business == null) {
                throw new ThrowJsonException("register_franchisee_empty");
            }
            //加密密码
            String pass = PwdEncode.encodePwd(passWord);
            if (!business.getPassWord().equals(pass)) {
                throw new ThrowJsonException("register_password_error");
            }
            //判断账号是否被冻结
            if (!FunctionUtils.isEquals(business.getStatus(), StaticUtils.STATUS_SUCCESS)) {
                throw new ThrowJsonException("register_not_pass");
            }
            //加密 封装 存入redis
            loginDto.setChannel(loginRegisterVo.getChannel());
            loginDto.setTid(business.getId());
            loginDto.setType(StaticUtils.LOGIN_BUSINESS);

            BusinessHomeVo businessHome = businessService.getBusinessHome(business.getId());
            // 生成token
            RestResult loginToken = loginTokenService.getLoginToken(loginDto, LoginChannelEnums.getLoginEnum(loginRegisterVo.getChannel()));
            if (!loginToken.status) {
                return loginToken;
            }
            businessHome.setToken(loginToken.getData().toString());
            return RestResult.success("success", businessHome);
        }
        return RestResult.fail("fail");
    }

    private RestResult aliProgramLogin(LoginRegisterVo loginRegisterVo) {
        if (StringUtils.isBlank(loginRegisterVo.getAuthCode())) {
            return RestResult.fail("授权失败");
        }
        try {
            AlipaySystemOauthTokenResponse accessTokenResponse = AliLoginUtil.getAccessToken(loginRegisterVo.getAuthCode());
            String accessToken = accessTokenResponse.getAccessToken();
            AlipayUserInfoShareResponse aliUserInfo = AliLoginUtil.getAliUserInfo(accessToken);
            System.out.println("获取到的用户头像" + aliUserInfo.getAvatar());
            System.out.println("获取到的性别" + aliUserInfo.getGender());
            System.out.println("获取到的昵称" + aliUserInfo.getNickName());
            System.out.println("获取到的手机" + aliUserInfo.getPhone());
            System.out.println("获取到的用户Id" + aliUserInfo.getUserId());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return RestResult.fail("授权失败");
        }
        return RestResult.success("success");
    }


    private RestResult appCustomerLogin(LoginRegisterVo loginRegisterVo, LoginDto loginDto, String passWord, String phone) {
        // 查询用户
        CCustomer customer = customerService.getOne(new QueryWrapper<CCustomer>().eq("phone", phone));
        if (customer == null) {
            throw new ThrowJsonException("register_user_empty");
        }
        //加密密码
        String pass = PwdEncode.encodePwd(passWord);
        if (!customer.getPassWord().equals(pass)) {
            throw new ThrowJsonException("register_password_error");
        }
        //判断账号是否被冻结
        if (customer.getStatus() == StaticUtils.STATUS_NO) {
            throw new ThrowJsonException("register_user_freeze");
        }
        //加密 封装 存入redis
        loginDto.setChannel(loginRegisterVo.getChannel());
        loginDto.setTid(customer.getId());
        loginDto.setType(StaticUtils.LOGIN_CUSTOMER);
        // 生成token
        RestResult result = loginTokenService.getLoginToken(loginDto, LoginChannelEnums.getLoginEnum(loginRegisterVo.getChannel()));
        if (!result.status) {
            return result;
        }
        CustomerHomeVo vo = personalInform(customer.getId());
        vo.setToken(result.getData().toString());
        return RestResult.success("success", vo);
    }

    /**
     * 个人资料
     *
     * @param cid
     * @return
     */
    @Override
    public CustomerHomeVo personalInform(Integer cid) {
        CustomerHomeVo homeVo = new CustomerHomeVo();
        CCustomer customer = customerMapper.selectById(cid);
        homeVo.setPhone(customer.getPhone());
        homeVo.setCreateTime(customer.getCreateTime());
        homeVo.setEmail(customer.getEmail());
        homeVo.setStatus(customer.getStatus());
        homeVo.setToken(customer.getToken());
        homeVo.setIsValid(customer.getIsReal());
        homeVo.setHeadUrl(customer.getHeadUrl());
        homeVo.setNickName(customer.getNickName());
        return homeVo;
    }

    /**
     * 修改个人资料
     * @param customer
     * @return
     */
    @Override
    public RestResult updatePersonalInform(CCustomer customer) {
        int i = customerMapper.updateById(customer);
        if (i > 0) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 修改登录或支付密码
     *
     * @param verCode  验证码
     * @param password 密码
     * @param type     1登录密码 2支付密码
     * @param cid      用户id
     * @param userType 1用户 2加盟商
     * @return
     */
    @Override
    public RestResult updateLoginPass(String verCode, String password, Integer cid, Integer type, Integer userType) {
        if (StringUtils.isBlank(verCode)) {
            return RestResult.fail("user_code_empty");
        }
        //TODO 短信验证未写
        //密码加密
        String pass = PwdEncode.encodePwd(password);
        switch (userType) {
            //用户
            case StaticUtils.TYPE_PASS_WORD:
                CCustomer customer = customerMapper.selectById(cid);
                if (customer == null) {
                    return RestResult.fail("user_customer_empty");
                }
                switch (type) {
                    //修改登录密码
                    case StaticUtils.TYPE_PASS_WORD:
                        /*boolean passPwd = FunctionUtils.validLoginPwd(password);
                        if (!passPwd) {
                            return RestResult.fail("user_login_pass_error");
                        }*/
                        if (StringUtils.isBlank(password)) {
                            return RestResult.fail("user_login_pass_empty");
                        }
                        customer.setPassWord(pass);
                        break;
                    //修改支付密码
                    case StaticUtils.TYPE_PAY_WORD:
                        /*boolean payPwd = FunctionUtils.validPayword(password);
                        if (!payPwd) {
                            return RestResult.fail("user_pay_pass_error");
                        }*/
                        if (StringUtils.isBlank(password)) {
                            return RestResult.fail("user_pay_pass_empty");
                        }
                        customer.setPayWord(pass);
                        break;
                }
                int i = customerMapper.updateById(customer);
                if (i == 0) {
                    return RestResult.fail("fail");
                }
                return RestResult.success("success");
            //加盟商
            case StaticUtils.TYPE_FRANCHISEE:
                BBusiness business = bbusinessService.selectById(cid);
                switch (type) {
                    //修改登录密码
                    case StaticUtils.TYPE_PASS_WORD:
                        /*boolean passPwd = FunctionUtils.validLoginPwd(password);
                        if (!passPwd) {
                            return RestResult.fail("user_login_pass_error");
                        }*/
                        if (StringUtils.isBlank(password)) {
                            return RestResult.fail("user_login_pass_empty");
                        }
                        business.setPassWord(pass);
                        break;
                    //修改支付密码
                    case StaticUtils.TYPE_PAY_WORD:
                        /*boolean payPwd = FunctionUtils.validPayword(password);
                        if (!payPwd) {
                            return RestResult.fail("user_pay_pass_error");
                        }*/
                        if (StringUtils.isBlank(password)) {
                            return RestResult.fail("user_pay_pass_empty");
                        }
                        business.setPayWord(pass);
                        break;
                }
                boolean b = bbusinessService.updateById(business);
                if (!b) {
                    return RestResult.fail("fail");
                }
                return RestResult.success("success");
        }
        return RestResult.fail("success");
    }

    /**
     * 找回密码
     *
     * @param phone
     * @param passWord
     * @param validCode
     * @return
     */
    @Override
    public RestResult forgetPassword(String phone, String password, String verCode) {
        if (StringUtils.isBlank(phone)) {
            return RestResult.fail("register_phone_empty");
        }
        if (StringUtils.isBlank(password)) {
            return RestResult.fail("register_pass_empty");
        }
        if (StringUtils.isBlank(verCode)) {
            return RestResult.fail("verifycode_not_exist");
        }
        CCustomer customer = customerMapper.selectOne(new QueryWrapper<CCustomer>().eq("phone", phone));
        if (customer == null) {
            return RestResult.fail("login_not_exist");
        }
        //TODO 短信验证未写

        //密码加密
        String pass = PwdEncode.encodePwd(password);

        boolean passPwd = FunctionUtils.validLoginPwd(password);
        if (!passPwd) {
            return RestResult.fail("user_login_pass_error");
        }
        customer.setPassWord(pass);
        int i = customerMapper.updateById(customer);
        if (i == 0) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }


}
