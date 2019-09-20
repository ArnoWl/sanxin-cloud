package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysCashDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

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

    /**
     * 处理提现申请
     * @param cashDetail
     * @param payWord 支付密码
     * @param validCode 验证码
     * @return
     */
    RestResult handleCashApply(SysCashDetail cashDetail, String payWord, String validCode);

    /**
     * 用户点击提现申请返回判断支付方式(押金)查询最后一条充值记录
     * @param cid
     * @return
     */
    RestResult selectLimt(Integer cid);

    /**
     * 确认申请提现
     * @param cid
     * @return
     */
    RestResult marginWithdraw(Integer cid);

    /**
     * 查询提现总额
     * @return
     */
    BigDecimal sumCashMoney();
}
