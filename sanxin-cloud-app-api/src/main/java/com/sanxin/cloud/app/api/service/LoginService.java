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
 *
 * @author xiaoky
 * @date 2019-09-16
 */
public interface LoginService {

    /**
     * 第三方绑定(用户)
     *
     * @param accessToken 第三方token
     * @param id          第三方id
     * @param type        1facebook 登录  2google登录
     * @param passWord    密码
     * @param phone       手机号
     * @param verCode     验证码
     * @return
     * @throws Exception
     */
    RestResult bindingPhone(String accessToken, String id, Integer type, String passWord, String phone, String verCode, String areaCode,String picture);

    /**
     * 第三方登录(用户)
     * @param accessToken
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    RestResult tripartiteLogin(String accessToken, String id, Integer type) throws Exception;

    /**
     * 登录
     *
     * @param loginRegisterVo 登录信息
     * @return
     */
    RestResult doLogin(LoginRegisterVo loginRegisterVo);

    /**
     * 个人资料
     *
     * @param cid
     * @return
     */
    CustomerHomeVo personalInform(Integer cid);

    /**
     * 修改个人资料
     *
     * @param customer
     * @return
     */
    RestResult updatePersonalInform(CCustomer customer);

    /**
     * 绑定邮箱
     * @param cid
     * @param email
     * @return
     */
    RestResult updateEmail(Integer cid, String email);

    /**
     * 修改登录或支付密码
     *
     * @param verCode  验证码
     * @param password 密码
     * @param type     1登录密码 2支付密码
     * @param cid      用户id
     * @return
     */
    RestResult updateLoginPass(String verCode, String password, Integer cid, Integer type, Integer userType);

    /**
     * 找回密码
     *
     * @param phone
     * @param password
     * @param verCode
     * @return
     */
    RestResult forgetPassword(String phone, String password, String verCode);

}
