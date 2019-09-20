package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.service.CAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private CAccountService accountService;
    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 我的押金明细
     * @return
     */
    @GetMapping(value = MappingUtils.MY_DEPOSIT)
    public RestResult queryMyDeposit() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.queryMyDeposit(cid);
    }
}
