package com.sanxin.cloud.netty.enums;

/**
 * @author arno
 * @version 1.0
 * @date 2019-09-17
 */
public enum CommandEnums {
    x60("60","机柜登陆及响应"),
    x61("61","心跳及响应"),
    x62("62","查询机柜软件版本号及响应"),
    x63("63","设置机柜服务器地址及响应"),
    x64("64","查询机柜库存及响应"),
    x65("65","借充电宝及响应"),
    x66("66","还充电宝及响应"),
    x67("67","远程重启机柜及响应"),
    x68("68","远程升级及响应"),
    x69("69","查询 ICCID"),
    x6A("6A","查询服务器地址及响应"),
    x6B("6B","查询机柜库存充电宝数量"),
    x80("80","强制弹出充电宝");


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

    private CommandEnums(String command,String remark){
        this.command=command;
        this.remark=remark;
    }

    public static CommandEnums getCommandFun(String command){
        for(CommandEnums e:CommandEnums.values()){
            if(e.getCommand().equals(command)){
                return e;
            }
        }
        return null;
    }
}
