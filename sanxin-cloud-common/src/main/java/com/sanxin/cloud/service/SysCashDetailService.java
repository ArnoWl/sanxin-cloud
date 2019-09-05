package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysCashDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 余额提现记录表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
public interface SysCashDetailService extends IService<SysCashDetail> {

    /**
     * 处理提现通过\驳回
     * @param id
     * @param status 状态
     * @return
     */
    RestResult handleCashStatus(Integer id, Integer status);
}
