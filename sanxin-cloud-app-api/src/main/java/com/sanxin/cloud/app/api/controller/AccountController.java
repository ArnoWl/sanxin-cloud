package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.common.AccountMapping;
import com.sanxin.cloud.app.api.common.OrderMapping;
import com.sanxin.cloud.app.api.service.AccountService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.entity.CTimeDetail;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private CAccountService cAccountService;

    /**
     * 我的押金明细
     * @return
     */
    @RequestMapping(value = AccountMapping.MY_DEPOSIT)
    public RestResult queryMyDeposit(SPage<CMarginDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.queryMyDeposit(page,cid);
    }

    /**
     * 我的钱包
     * @return
     */
    @RequestMapping(value = AccountMapping.MY_PURSE)
    public RestResult queryMyPurse() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.queryMyPurse(cid);
    }

    /**
     * 余额明细
     * @return
     */
    @RequestMapping(value = AccountMapping.BALANCE_DETAIL)
    public RestResult queryBalanceDetail(SPage<CMoneyDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.queryBalanceDetail(page,cid);
    }

    /**
     * 我要充值显示余额
     * @return
     */
    @RequestMapping(value = AccountMapping.GET_BALANCE)
    public RestResult getBalance() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.getBalance(cid);
    }

    /**
     * 用户时长明细
     * @return
     */
    @RequestMapping(value = AccountMapping.TIME_DETAIL)
    public RestResult queryTimeDetail(SPage<CTimeDetail> page) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.queryTimeDetail(page,cid);
    }

    /**
     * 购买时长礼包
     * @return
     */
    @RequestMapping(value = AccountMapping.GET_BUY_GIFT)
    public RestResult getBuyGift() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.getBuyGift(cid);
    }

    /**
     * 支付购买时长
     * @param id 礼包id
     * @param type 支付类型1余额
     * @param payPass 支付密码
     * @return
     */
    @RequestMapping(value = AccountMapping.PAY_TIME_GIFT)
    public RestResult payTimeGift(Integer id,Integer type,String payPass) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.payTimeGift(cid,id,type,payPass);
    }

    /**
     * 借充电宝扫码时判断是否交了押金
     * @return
     */
    @RequestMapping(AccountMapping.VALID_RECHARGE_DEPOSIT)
    public RestResult validRechargeDeposit() {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        CAccount account = cAccountService.getByCid(cid);
        if (account == null) {
            return RestResult.success("data_exception");
        }
        return RestResult.success("success", account.getRechargeDeposit());
    }

    /**
     * 充值押金
     * @param payWord 支付密码-选择余额支付需要
     * @param payType 支付方式
     * @param payChannel 支付渠道
     * @param freeSecret 是否免密支付
     * @return
     */
    @RequestMapping(value = AccountMapping.RECHARGE_DEPOSIT)
    public RestResult rechargeDeposit(String payWord, Integer payType, Integer payChannel, Integer freeSecret) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        return accountService.handleRechargeDeposit(cid, payWord, payType, payChannel, freeSecret);
    }

    /**
     * 支付方式
     * @param type 支付渠道类型
     * @return
     */
    @RequestMapping(value = AccountMapping.QUERY_PAY_TYPE_LIST)
    public RestResult queryPayTypeList(Integer type) {
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        Map<String, Object> map = accountService.queryPayTypeList(type,cid);
        return RestResult.success("success", map);
    }

}
