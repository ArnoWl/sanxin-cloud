package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.AccountService;
import com.sanxin.cloud.app.api.service.LoginService;
import com.sanxin.cloud.app.api.service.RegistService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.sms.SMSSender;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.dto.CustomerHomeVo;
import com.sanxin.cloud.dto.ProgramBindVo;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.GiftHour;
import com.sanxin.cloud.enums.LoginChannelEnums;
import com.sanxin.cloud.enums.TimeGiftEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CAccountMapper;
import com.sanxin.cloud.mapper.GiftHourMapper;
import com.sanxin.cloud.service.CCustomerService;
import com.sanxin.cloud.service.system.login.LoginDto;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private GiftHourMapper giftHourMapper;
    @Autowired
    private AccountService accountService;

    /**
     * 发送验证码
     *
     * @param phone
     * @param areaCode
     * @return
     */
    @Override
    public RestResult sendVerCode(String phone, String areaCode) {
        RestResult result = SMSSender.sendSms(phone,areaCode);
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
        account.setHour(accountService.payReceiveTimeGift(customer.getId()));
        account.setCid(customer.getId());
        int insert = accountMapper.insert(account);
        if (insert > 0 && save) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    @Override
    public RestResult handleProgramBindPhone(ProgramBindVo vo) {
        // 校验验证码
        RestResult validCode = SMSSender.validSms((vo.getAreaCode() + vo.getPhone()), vo.getVerCode());
        if (!validCode.status) {
            return validCode;
        }
        // 先根据手机号查询用户
        QueryWrapper<CCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", vo.getPhone());
        CCustomer customer = customerService.getOne(queryWrapper);
        // 如果当前手机号没有注册过
        if (customer == null) {
            customer = new CCustomer();
            // 注册一个用户
            customer.setPhone(vo.getPhone());
            customer.setNickName(vo.getPhone());
            customer.setAreaCode(vo.getAreaCode());
            boolean save = customerService.save(customer);
            CAccount account = new CAccount();
            account.setCid(customer.getId());
            account.setHour(accountService.payReceiveTimeGift(customer.getId()));
            int insert = accountMapper.insert(account);
            if (insert <= 0 || !save) {
                throw new ThrowJsonException("绑定失败");
            }
        } else {
            // 判断是否绑定了其它小程序账号
            if (StringUtils.isNotBlank(customer.getUserId())) {
                return RestResult.fail("当前手机号已绑定其它支付宝账户");
            }
        }
        customer.setUserId(vo.getUserId());
        customerService.updateById(customer);
        LoginDto loginDto = LoginDto.getInstance();
        //加密 封装 存入redis
        loginDto.setChannel(LoginChannelEnums.ALI_PROGRAM.getChannel());
        loginDto.setTid(customer.getId());
        loginDto.setType(StaticUtils.LOGIN_CUSTOMER);
        loginDto.setUserId(customer.getUserId());
        // 生成token
        RestResult result = loginTokenService.getLoginToken(loginDto, LoginChannelEnums.ALI_PROGRAM);
        if (!result.status) {
            return result;
        }
        CustomerHomeVo returnVo = loginService.personalInform(customer.getId());
        returnVo.setToken(result.getData().toString());
        return RestResult.success("success", returnVo);
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
        if (StringUtils.isEmpty(customer.getAreaCode())) {
            throw new ThrowJsonException("areaCode_not_exist");
        }
    }
}
