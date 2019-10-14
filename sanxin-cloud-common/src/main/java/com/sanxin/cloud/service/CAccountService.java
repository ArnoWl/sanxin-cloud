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
     * 通过cid查询用户账户信息
     * @param cid
     * @return
     */
    CAccount getByCid(Integer cid);

}
