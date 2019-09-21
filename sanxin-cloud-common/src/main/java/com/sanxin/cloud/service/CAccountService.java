package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.entity.CTimeDetail;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-30
 */
public interface CAccountService extends IService<CAccount> {

    /**
     * 我的押金明细
     * @param cid
     * @return
     */
    RestResult queryMyDeposit(SPage<CMarginDetail> page,Integer cid);

    /**
     * 统计押金金额
     * @return
     */
    BigDecimal sumDepositMoney();

    /**
     * 我的钱包
     * @param cid
     * @return
     */
    RestResult queryMyPurse(Integer cid);

    /**
     * 余额明细
     * @param cid
     * @return
     */
    RestResult queryBalanceDetail(SPage<CMoneyDetail> page, Integer cid);

    /**
     * 我要充值显示余额
     * @param cid
     * @return
     */
    RestResult getBalance(Integer cid);

    /**
     * 用户时长明细
     * @param page
     * @param cid
     * @return
     */
    RestResult queryTimeDetail(SPage<CTimeDetail> page, Integer cid);

    /**
     * 剩余时长
     * @param cid
     * @return
     */
    RestResult getTime(Integer cid);
}
