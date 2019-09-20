package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    private BBusinessService businessService;
    @Autowired
    private LoginTokenService loginTokenService;

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
            throw new ThrowJsonException("手机号不能为空");
        }
        if (StringUtils.isEmpty(passWord)) {
            throw new ThrowJsonException("密码不能为空");
        }

        LoginDto loginDto = LoginDto.getInstance();
        // 判断登录类型
        // 用户
        if (FunctionUtils.isEquals(StaticUtils.LOGIN_CUSTOMER, loginRegisterVo.getType())) {
            // 查询用户
            CCustomer customer = customerService.getOne(new QueryWrapper<CCustomer>().eq("phone", phone));
            if (customer == null) {
                throw new ThrowJsonException("用户不存在");
            }
            //加密密码
            String pass = PwdEncode.encodePwd(passWord);
            if (!customer.getPassWord().equals(pass)) {
                throw new ThrowJsonException("密码错误");
            }
            //判断账号是否被冻结
            if(customer.getStatus() == StaticUtils.STATUS_NO) {
                throw new ThrowJsonException("账号已被冻结，请联系管理员");
            }
            //加密 封装 存入redis
            loginDto.setChannel(LoginChannelEnums.APP.getChannel());
            loginDto.setTid(customer.getId());
            loginDto.setType(StaticUtils.LOGIN_CUSTOMER);
            // 生成token
            return loginTokenService.getLoginToken(loginDto, LoginChannelEnums.APP);
        } else if (FunctionUtils.isEquals(StaticUtils.LOGIN_BUSINESS, loginRegisterVo.getType())) {
            // 加盟商
            BBusiness business = businessService.getOne(new QueryWrapper<BBusiness>().eq("phone", phone));
            if (business == null) {
                throw new ThrowJsonException("用户不存在");
            }
            //加密密码
            String pass = PwdEncode.encodePwd(passWord);
            if (!business.getPassWord().equals(pass)) {
                throw new ThrowJsonException("密码错误");
            }
            //判断账号是否被冻结
            if(!FunctionUtils.isEquals(business.getStatus(), StaticUtils.STATUS_SUCCESS)) {
                throw new ThrowJsonException("未审核通过");
            }
            //加密 封装 存入redis
            loginDto.setChannel(LoginChannelEnums.APP.getChannel());
            loginDto.setTid(business.getId());
            loginDto.setType(StaticUtils.LOGIN_BUSINESS);
            // 生成token
            return loginTokenService.getLoginToken(loginDto, LoginChannelEnums.APP);
        }
        return RestResult.fail("fail");
    }

    /**
     * 个人资料
     * @param cid
     * @return
     */
    @Override
    public RestResult personalInform(Integer cid) {
        CCustomer customer = customerMapper.selectById(cid);
        return RestResult.success(customer);
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
    public RestResult updateLoginPass(String phone, String verCode, String password, Integer cid, Integer type) {
        if (StringUtils.isBlank(phone)) {
            return RestResult.fail("user_phone_empty");
        }
        if (StringUtils.isBlank(verCode)) {
            return RestResult.fail("user_code_empty");
        }
        //TODO 短信验证未写
        //密码加密
        String pass = PwdEncode.encodePwd(password);
        CCustomer customer = customerMapper.selectOne(new QueryWrapper<CCustomer>().eq("id",cid).eq("phone",phone));
        if (customer == null) {
            return RestResult.fail("user_customer_empty");
        }
        switch (type){
            //修改登录密码
            case 1:
                if (StringUtils.isBlank(password)) {
                    return RestResult.fail("user_login_pass_empty");
                }
                customer.setPassWord(pass);
                break;
                //修改支付密码
            case 2:
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

}
