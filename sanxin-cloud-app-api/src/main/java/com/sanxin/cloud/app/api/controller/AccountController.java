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

    /**
     * 我的押金明细
     * @return
     */
    @GetMapping(value = MappingUtils.MY_PURSE)
    public RestResult queryMyPurse() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.queryMyPurse(cid);
    }

    /**
     * 余额明细
     * @return
     */
    @GetMapping(value = MappingUtils.BALANCE_DETAIL)
    public RestResult queryBalanceDetail() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.queryBalanceDetail(cid);
    }

    /**
     * 我要充值显示余额
     * @return
     */
    @GetMapping(value = MappingUtils.GET_BALANCE)
    public RestResult getBalance() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.getBalance(cid);
    }
}
