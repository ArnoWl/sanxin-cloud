package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.app.api.service.LoginService;
import com.sanxin.cloud.common.Constant;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
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
import com.sanxin.cloud.enums.RandNumType;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.CCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
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
        if (StringUtils.isEmpty(phone)) {
            throw new ThrowJsonException("register_phone_empty");
        }
        if (StringUtils.isEmpty(passWord)) {
            throw new ThrowJsonException("register_pass_empty");
        }

        LoginDto loginDto = LoginDto.getInstance();
        // 判断登录类型
        // 用户
        if (FunctionUtils.isEquals(StaticUtils.LOGIN_CUSTOMER, loginRegisterVo.getType())) {
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
            if(customer.getStatus() == StaticUtils.STATUS_NO) {
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
            CustomerHomeVo custome = personalInform(customer.getId());
            custome.setToken(result.getData().toString());
            return RestResult.success("success", custome);
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
            if(!FunctionUtils.isEquals(business.getStatus(), StaticUtils.STATUS_SUCCESS)) {
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
            return RestResult.success("success",businessHome);
        }
        return RestResult.fail("fail");
    }

    /**
     * 个人资料
     * @param cid
     * @return
     */
    @Override
    public CustomerHomeVo personalInform(Integer cid) {
        CustomerHomeVo homeVo=new CustomerHomeVo();
        CCustomer customer = customerMapper.selectById(cid);
        homeVo.setPhone(customer.getPhone());
        homeVo.setCreateTime(customer.getCreateTime());
        homeVo.setEmail(customer.getEmail());
        homeVo.setStatus(customer.getStatus());
        homeVo.setToken(customer.getToken());
        homeVo.setIsValid(customer.getIsReal());
        homeVo.setHeadUrl(customer.getHeadUrl());
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
     * @param phone 手机号
     * @param verCode 验证码
     * @param password 密码
     * @param type 1登录密码 2支付密码
     * @param cid 用户id
     * @return
     */
    @Override
    public RestResult updateLoginPass(String verCode, String password, Integer cid, Integer type) {
        if (StringUtils.isBlank(verCode)) {
            return RestResult.fail("user_code_empty");
        }
        //密码校验格式
        boolean validPwd = FunctionUtils.validLoginPwd(password);
        if (!validPwd) {
            return RestResult.fail("user_login_pass_error");
        }
        //TODO 短信验证未写
        //密码加密
        String pass = PwdEncode.encodePwd(password);
        CCustomer customer = customerMapper.selectById(cid);
        if (customer == null) {
            return RestResult.fail("user_customer_empty");
        }
        switch (type){
            //修改登录密码
            case StaticUtils.TYPE_PASS_WORD:
                if (StringUtils.isBlank(password)) {
                    return RestResult.fail("user_login_pass_empty");
                }
                customer.setPassWord(pass);
                break;
                //修改支付密码
            case StaticUtils.TYPE_PAY_WORD:
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
    }

    /**
     * 找回密码
     * @param phone
     * @param passWord
     * @param validCode
     * @return
     */
    @Override
    public RestResult forgetPassword(String phone,String passWord,String validCode) {
        if (StringUtils.isBlank(phone)) {
            return RestResult.fail("register_phone_empty");
        }
        if (StringUtils.isBlank(passWord)) {
            return RestResult.fail("register_pass_empty");
        }
        if (StringUtils.isBlank(validCode)) {
            return RestResult.fail("verifycode_not_exist");
        }
        CCustomer customer = customerMapper.selectOne(new QueryWrapper<CCustomer>().eq("phone", phone));
        if (customer == null) {
            return RestResult.fail("login_not_exist");
        }
        //TODO 短信验证未写

        //密码加密
        String pass = PwdEncode.encodePwd(passWord);
        customer.setPassWord(passWord);
        int i = customerMapper.updateById(customer);
        if (i == 0) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }


}
