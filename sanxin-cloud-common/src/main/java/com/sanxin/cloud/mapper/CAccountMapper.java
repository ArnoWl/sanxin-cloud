package com.sanxin.cloud.mapper;

import com.sanxin.cloud.entity.CAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-30
 */
public interface CAccountMapper extends BaseMapper<CAccount> {

    /**
     * 统计押金金额
     * @return
     */
    BigDecimal sumDepositMoney();

    /**
     * 修改账户
     * @param account
     * @return
     */
    int updateLockVersion(CAccount account);
}
