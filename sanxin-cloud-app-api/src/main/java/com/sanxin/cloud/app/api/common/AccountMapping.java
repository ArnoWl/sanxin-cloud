package com.sanxin.cloud.app.api.common;

/**
 * 账户接口地址映射
 * @author
 * @date 2019-10-09
 */
public class AccountMapping {
    /**我的押金*/
    public static final String MY_DEPOSIT="/myDeposit";

    /**开启关闭免密支付*/
    public static final String FREE_SECRET="/freeSecret";

    /**查询充值方式*/
    public static final String SELECT_LIMT="/selectLimt";

    /**押金提现申请*/
    public static final String MARGIN_WITHDRAW="/marginWithdraw";

    /**我的钱包*/
    public static final String MY_PURSE="/myPurse";

    /**我的钱包*/
    public static final String BALANCE_DETAIL="/balanceDetail";

    /**我要充值显示余额*/
    public static final String GET_BALANCE="/getBalance";

    /**用户时长明细*/
    public static final String TIME_DETAIL="/timeDetail";

    /**剩余时长*/
    public static final String GET_BUY_GIFT="/getBuyGift";

    /**提交购买时长*/
    public static final String PAY_TIME_GIFT="/payTimeGift";

    /** 借充电宝扫码时判断是否交了押金*/
    public static final String VALID_RECHARGE_DEPOSIT = "/validRechargeDeposit";

    /**充值押金*/
    public static final String RECHARGE_DEPOSIT="/rechargeDeposit";
    /**支付方式列表*/
    public static final String QUERY_PAY_TYPE_LIST="/queryPayTypeList";
    /**支付方式列表——小程序*/
    public static final String QUERY_PAY_TYPE_LIST_FOR_PROGRAM="/queryPayTypeListForProgram";
    /** 充值押金页面数据*/
    public static final String GET_RECHARGE_MSG="/getRechargeMsg";
    /** 借用成功页面数据*/
    public static final String GET_LEND_SUCCESS_MSG="/getLendSuccessMsg";
}
