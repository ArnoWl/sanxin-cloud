package com.sanxin.cloud.app.api.common;

/**
 * 商家个人信息等资料地址映射
 * @author xiaoky
 * @date 2019-09-18
 */
public class BusinessMapping {
    /** 商家个人资料*/
    public static final String GET_BUSINESS_INFO="/getBusinessInfo";
    /** 编辑商家个人资料*/
    public static final String EDIT_BUSINESS_INFO="/editBusinessInfo";
    /** 商家中心*/
    public static final String GET_BUSINESS_CENTER = "/getBusinessCenter";
    /** 编辑商家中心*/
    public static final String EDIT_BUSINESS_CENTER = "/editBusinessCenter";
    /** 查询商家首页数据*/
    public static final String GET_BUSINESS_HOME = "/getBusinessHome";
    /** 查询余额明细*/
    public static final String QUERY_MONEY_DETAIL_LIST = "/queryMoneyDetailList";
    /** 查询收益明细*/
    public static final String QUERY_INCOME_DETAIL_LIST = "/queryIncomeDetailList";
    /** 查询首页统计图*/
    public static final String QUERY_INCOME_STATISTICS = "/queryIncomeStatistics";
    /** 修改密码*/
    public static final String UPDATE_PASSWORD = "/updatePassword";
}
