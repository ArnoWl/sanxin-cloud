package com.sanxin.cloud.app.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.Constant;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.common.rest.RestResult;
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
}
