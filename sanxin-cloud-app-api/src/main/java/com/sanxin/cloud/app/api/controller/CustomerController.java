package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.common.CustomerMapping;
import com.sanxin.cloud.app.api.service.CustomerService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 个人中心(用户)
     * @return
     */
    @RequestMapping(value = CustomerMapping.GET_PERSON_CENTER)
    public RestResult getPersonCenter() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return customerService.getPersonCenter(cid);
    }

}
