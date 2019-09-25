package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.entity.CTimeDetail;
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
    @RequestMapping(value = MappingUtils.MY_DEPOSIT)
    public RestResult queryMyDeposit(SPage<CMarginDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.queryMyDeposit(page,cid);
    }

    /**
     * 我的押金明细
     * @return
     */
    @RequestMapping(value = MappingUtils.MY_PURSE)
    public RestResult queryMyPurse() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.queryMyPurse(cid);
    }

    /**
     * 余额明细
     * @return
     */
    @RequestMapping(value = MappingUtils.BALANCE_DETAIL)
    public RestResult queryBalanceDetail(SPage<CMoneyDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.queryBalanceDetail(page,cid);
    }

    /**
     * 我要充值显示余额
     * @return
     */
    @RequestMapping(value = MappingUtils.GET_BALANCE)
    public RestResult getBalance() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.getBalance(cid);
    }

    /**
     * 用户时长明细
     * @return
     */
    @RequestMapping(value = MappingUtils.TIME_DETAIL)
    public RestResult queryTimeDetail(SPage<CTimeDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.queryTimeDetail(page,cid);
    }

    /**
     * 剩余时长
     * @return
     */
    @RequestMapping(value = MappingUtils.GET_TIME)
    public RestResult getTime() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginTid(token);
        return accountService.getTime(cid);
    }
}
