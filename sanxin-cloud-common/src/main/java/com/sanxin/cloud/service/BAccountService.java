package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.BAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 加盟商账户表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-19
 */
public interface BAccountService extends IService<BAccount> {

    /**
     * 查询店铺账户信息
     * @param bid
     * @return
     */
    BAccount getByBid(Integer bid);
}
