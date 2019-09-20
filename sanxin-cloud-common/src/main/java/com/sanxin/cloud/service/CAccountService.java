package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.CAccount;
import com.baomidou.mybatisplus.extension.service.IService;

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
    RestResult queryMyDeposit(Integer cid);

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
    RestResult queryBalanceDetail(Integer cid);

    /**
     * 我要充值显示余额
     * @param cid
     * @return
     */
    RestResult getBalance(Integer cid);
}
