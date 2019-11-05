package com.sanxin.cloud.mapper;

import com.sanxin.cloud.entity.BAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 加盟商账户表 Mapper 接口
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-19
 */
public interface BAccountMapper extends BaseMapper<BAccount> {

    /**
     * 修改商家账户信息
     * @param account
     * @return
     */
    int updateLockVersion(BAccount account);
}
