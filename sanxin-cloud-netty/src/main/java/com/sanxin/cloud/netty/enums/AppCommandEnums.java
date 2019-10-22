package com.sanxin.cloud.netty.enums;

/**
 *
 * @author xiaoky
 * @date 2019-10-11
 */
public enum AppCommandEnums {
    /**
     * app连接登陆及响应
     */
    x10000("10000", "app连接登陆及响应"),
    x10001("10001", "app心跳以及响应"),
    x10002("10002", "app借充电宝信息及响应"),
    x10003("10003", "app还充电宝响应"),
    x10004("10004", "借充电宝进度条返回");

    /**
     * 指令
     */
    private String command;

    /**
     * 描述
     */
    private String remark;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private AppCommandEnums(String command,String remark){
        this.command=command;
        this.remark=remark;
    }

    public static AppCommandEnums getCommandFun(String command){
        for(AppCommandEnums e:AppCommandEnums.values()){
            if(e.getCommand().equals(command)){
                return e;
            }
        }
        return null;
    }
}
