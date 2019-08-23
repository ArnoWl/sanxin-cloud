package com.sanxin.cloud.admin.web.controller;

import com.sanxin.cloud.admin.api.service.LoginService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysConfig;
import com.sanxin.cloud.mapper.SysConfigMapper;
import com.sanxin.cloud.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-22
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;



    @PostMapping("/loginOut")
    public RestResult loginOut(){
        List<SysConfig> list=loginService.queryList();
        RestResult restResult=RestResult.success("退出成功",list);
        return  restResult;
    }
}
