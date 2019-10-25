package com.sanxin.cloud.app.api.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.entity.CTimeDetail;

import java.util.Map;

/**
 * 用户账户Service
 *
 * @author xiaoky
 * @date 2019-10-09
 */
public interface AccountService {

    /**
     * 我的押金明细
     *
     * @param cid
     * @return
     */
    RestResult queryMyDeposit(SPage<CMarginDetail> page, Integer cid);

    /**
     * 我的钱包
     *
     * @param cid
     * @return
     */
    RestResult queryMyPurse(Integer cid);

    /**
     * 余额明细
     *
     * @param cid
     * @return
     */
    RestResult queryBalanceDetail(SPage<CMoneyDetail> page, Integer cid);

    /**
     * 我要充值显示余额
     *
     * @param cid
     * @return
     */
    RestResult getBalance(Integer cid);

    /**
     * 用户时长明细
     *
     * @param page
     * @param cid
     * @return
     */
    RestResult queryTimeDetail(SPage<CTimeDetail> page, Integer cid);

    /**
     * 剩余时长
     *
     * @param cid
     * @return
     */
    RestResult getBuyGift(Integer cid);

    /**
     * 支付购买时长
     * @param cid 用户di
     * @param giftId 礼包id
     * @param payType 支付类型1余额  2泰国本地银行卡
     * @param payWord 支付密码
     * @return
     */
    RestResult payTimeGift(Integer cid, Integer giftId,Integer payType,String payWord);

    /**
     * 购买充电宝
     * @param cid
     * @param payType
     * @param payWord
     * @return
     */
    RestResult payBuyPowerBank(Integer cid, Integer payType, String payWord);


    /**
     * 充值押金处理
     * @param cid
     * @param payWord    支付密码-选择余额支付需要
     * @param payType    支付方式见PayTypeEnums
     * @param payChannel 支付渠道见LoginChannelEnums
     * @param freeSecret 是否免密支付
     * @return
     */
    RestResult handleRechargeDeposit(Integer cid, String payWord, Integer payType, Integer payChannel, Integer freeSecret);

    /**
     * 支付方式列表
     * @param type 支付渠道
     * @param cid 用户id
     * @return
     */
    Map<String, Object> queryPayTypeList(Integer type, Integer cid);

    /**
     * 支付方式列表——小程序
     * @param cid 用户id
     * @return
     */
    Map<String, Object> queryPayTypeListForProgram(Integer cid);

    /**
     * 获取充值页面数据
     * @return
     */
    Map<String, Object> getRechargeMsg();

    /**
     * 开启关闭免密支付
     * @param cid
     * @return
     */
    RestResult freeSecret(Integer cid,String payWord);

}
