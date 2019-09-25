package com.sanxin.cloud.config.login;

import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.DESEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.enums.LoginChannelEnums;
import com.sanxin.cloud.exception.LoginOutException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统一会员登录业务层 /退出登录
 * 登录渠道来源包含  APP ，小程序，微信，PC，H5
 * APP，PC,H5分别为单点登录，意思表示每个端口自己的单点登录
 *
 * @author arno
 */
@Service
public class LoginTokenService {


    @Autowired
    private RedisUtilsService redisUtilsService;

    /**
     * 统一处理登录
     *
     * @param loginDto
     * @param enums
     * @return
     */
    public RestResult getLoginToken(LoginDto loginDto, LoginChannelEnums enums) {
        if (loginDto == null || loginDto.getTid() == null) {
            return RestResult.fail("Unique primary can't be Null");
        }
        if (loginDto.getChannel() == null) {
            return RestResult.fail("Error in landing channel");
        }
        loginDto.setChannel(enums.getChannel());
        //将对象转换JSONObject string
        String object = JSONObject.toJSONString(loginDto);
        //加密参数
        //每个渠道只能同时登陆一个人
        //生成键名
        String token = DESEncode.getInstance().encrypt(loginDto.getTid() + "_" + loginDto.getChannel() + "_" + DateUtil.currentTimeMillis());
        String key = loginDto.getTid() + "_token";
        //处理登录
        //APP PC WAP 可能存在登陆失效时间
        //小程序 微信不存在过期说法
        switch (enums) {
            case APP:
                //校验是否已经登陆 如果存在登陆信息 让其下线
                //保存
                if (FunctionUtils.isEquals(StaticUtils.STATUS_YES, loginDto.getNeedTimeout())) {
                    redisUtilsService.setKey(token, object, loginDto.getTimemout());
                    redisUtilsService.setKey(key, token, loginDto.getTimemout());
                } else {
                    redisUtilsService.setKey(token, object);
                    redisUtilsService.setKey(key, token);
                }
                break;
            case ALI_PROGRAM:
                if (StringUtils.isBlank(loginDto.getUserId())
                        && FunctionUtils.isEquals(StaticUtils.LOGIN_CUSTOMER, loginDto.getType())) {
                    return RestResult.fail("Small program OpenID can't be Null");
                }
                redisUtilsService.setKey(token, object);
                redisUtilsService.setKey(key, token);
                break;
            default:
                break;
        }
        return RestResult.success("登陆成功", token);
    }

    /**
     * 统一退出登录
     *
     * @param token token
     * @return
     */
    public RestResult loginOut(String token, Integer type) {
        if (StringUtils.isBlank(token)) {
            return RestResult.fail("Token can't be Null");
        }
        String decrypt = DESEncode.getInstance().decrypt(token);
        if (StringUtils.isBlank(decrypt)) {
            return RestResult.success("退出成功");
        }
        LoginDto loginDto = JSONObject.parseObject(decrypt, LoginDto.class);
        if (loginDto == null || loginDto.getTid() == null) {
            return RestResult.success("退出成功");
        }
        //生成键名
        String key = loginDto.getTid() + "_token";
        redisUtilsService.deleteKey(token);
        redisUtilsService.deleteKey(key);
        return RestResult.success("退出成功");
    }

    /**
     * 用户端拦截器调用此方法
     * 校验当前登陆渠道是否存在
     *
     * @param token token
     * @return 校验通过返回 登陆对象
     */
    public LoginDto validLoginLoginDto(String token) {
        if (StringUtils.isBlank(token)) {
            throw new LoginOutException("1000");
        }
        String decrypt = redisUtilsService.getKey(token);
        if (StringUtils.isBlank(decrypt)) {
            throw new LoginOutException("1000");
        }
        LoginDto loginDto = JSONObject.parseObject(decrypt, LoginDto.class);
        if (loginDto == null || loginDto.getTid() == null) {
            throw new LoginOutException("1000");
        }
        return loginDto;
    }

    /**
     * 用户端拦截器调用此方法
     * 校验当前登陆渠道是否存在
     *
     * @param token token
     * @return 校验通过返回 用户id
     */
    public Integer validLoginTid(String token) {
        if (StringUtils.isBlank(token)) {
            //Token can't be Null
            throw new LoginOutException("997");
        }
        String decrypt = redisUtilsService.getKey(token);
        if (StringUtils.isBlank(decrypt)) {
            //Logon information error
            throw new LoginOutException("998");
        }
        LoginDto loginDto = JSONObject.parseObject(decrypt, LoginDto.class);
        if (loginDto == null || loginDto.getTid() == null) {
            //Unique primary can't be Null
            throw new LoginOutException("999");
        }
        if (loginDto.getChannel() == null) {
            //Error in landing channel
            throw new LoginOutException("1000");
        }
        return loginDto.getTid();
    }

    /**
     * 用户端拦截器调用此方法
     * 校验当前登陆渠道是否存在
     *
     * @param token token
     * @return 校验通过返回 登录类型
     */
    public Integer validLoginType(String token) {
        if (StringUtils.isBlank(token)) {
            //Token can't be Null
            throw new LoginOutException("997");
        }
        String decrypt = redisUtilsService.getKey(token);
        if (StringUtils.isBlank(decrypt)) {
            //Logon information error
            throw new LoginOutException("998");
        }
        LoginDto loginDto = JSONObject.parseObject(decrypt, LoginDto.class);
        if (loginDto == null || loginDto.getTid() == null) {
            //Unique primary can't be Null
            throw new LoginOutException("999");
        }
        if (loginDto.getChannel() == null) {
            //Error in landing channel
            throw new LoginOutException("1000");
        }
        return loginDto.getType();
    }

    /**
     * 通过token获取cid
     * @param token 仅限传入用户端token,避免主键相同时用户端和商家端token互用
     * @return
     */
    public Integer validLoginCid(String token) {
        if (StringUtils.isBlank(token)) {
            //Token can't be Null
            throw new LoginOutException("997");
        }
        String decrypt = redisUtilsService.getKey(token);
        if (StringUtils.isBlank(decrypt)) {
            //Logon information error
            throw new LoginOutException("998");
        }
        LoginDto loginDto = JSONObject.parseObject(decrypt, LoginDto.class);
        if (loginDto == null || loginDto.getTid() == null) {
            //Unique primary can't be Null
            throw new LoginOutException("999");
        }
        if (!FunctionUtils.isEquals(loginDto.getType(), StaticUtils.LOGIN_CUSTOMER)) {
            throw new LoginOutException("999");
        }
        if (loginDto.getChannel() == null) {
            //Error in landing channel
            throw new LoginOutException("1000");
        }
        return loginDto.getTid();
    }

    /**
     * 通过token获取id
     * @param token 仅限传入商家端token,避免主键相同时用户端和商家端token互用
     * @return
     */
    public Integer validLoginBid(String token) {
        if (StringUtils.isBlank(token)) {
            //Token can't be Null
            throw new LoginOutException("997");
        }
        String decrypt = redisUtilsService.getKey(token);
        if (StringUtils.isBlank(decrypt)) {
            //Logon information error
            throw new LoginOutException("998");
        }
        LoginDto loginDto = JSONObject.parseObject(decrypt, LoginDto.class);
        if (loginDto == null || loginDto.getTid() == null) {
            //Unique primary can't be Null
            throw new LoginOutException("999");
        }
        if (!FunctionUtils.isEquals(loginDto.getType(), StaticUtils.LOGIN_BUSINESS)) {
            throw new LoginOutException("999");
        }
        if (loginDto.getChannel() == null) {
            //Error in landing channel
            throw new LoginOutException("1000");
        }
        return loginDto.getTid();
    }
}
