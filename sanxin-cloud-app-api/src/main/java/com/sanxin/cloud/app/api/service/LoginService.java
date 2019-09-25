package com.sanxin.cloud.app.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.Constant;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.dto.CustomerHomeVo;
import com.sanxin.cloud.dto.LoginRegisterVo;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.enums.RandNumType;
import com.sanxin.cloud.exception.ThrowJsonException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 登录Service
 * @author xiaoky
 * @date 2019-09-16
 */
public interface LoginService {
    /**
     * 登录
     * @param loginRegisterVo 登录信息
     * @return
     */
    RestResult doLogin(LoginRegisterVo loginRegisterVo);

    /**
     * 个人资料
     * @param cid
     * @return
     */
    CustomerHomeVo personalInform(Integer cid);

    /**
     * 修改个人资料
     * @param customer
     * @return
     */
    RestResult updatePersonalInform(CCustomer customer);

    /**
     * 修改登录或支付密码
     * @param verCode 验证码
     * @param password 密码
     * @param type 1登录密码 2支付密码
     * @param cid 用户id
     * @return
     */
    RestResult updateLoginPass(String verCode, String password, Integer cid,Integer type);

    /**
     * 找回密码
     * @param phone
     * @param passWord
     * @param validCode
     * @return
     */
    RestResult forgetPassword(String phone,String passWord,String validCode);

}
