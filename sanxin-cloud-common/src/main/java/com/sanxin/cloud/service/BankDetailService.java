package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BankDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 银行卡信息表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public interface BankDetailService extends IService<BankDetail> {

    /**
     * 添加银行卡
     * @param bankDetail
     * @return
     */
    RestResult addBankDetail(BankDetail bankDetail);

    /**
     * 修改银行卡
     * @param bankDetail
     * @return
     */
    RestResult updateBankDetail(BankDetail bankDetail);
}
