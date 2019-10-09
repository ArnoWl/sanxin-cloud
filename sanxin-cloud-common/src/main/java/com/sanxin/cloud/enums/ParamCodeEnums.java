package com.sanxin.cloud.enums;

/**
 * 系统参数配置code
 * @author xiaoky
 * @date 2019-10-09
 */
public enum ParamCodeEnums {
    USE_HOUR_MONEY("计费标准", "useHourMoney"),
    RECHARGE_DEPOSIT_MONEY("押金金额", "rechargeDepositMoney");

    private String name;
    private String code;

    private ParamCodeEnums(String name,String code){
        this.name=name;
        this.code=code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
