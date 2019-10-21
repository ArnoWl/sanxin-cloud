package com.sanxin.cloud.netty.config;

import com.alibaba.fastjson.JSON;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-21
 */
@NoArgsConstructor
@Data
public class CommandResult implements Serializable {


    /**
     * 请求是否成功
     * true成功 false 失败
     */
    public boolean status;

    /**
     * 状态码 1表示成功  -1表示失败
     */
    public String code;

    /**
     * 返回描述类容
     */
    public String msg;

    /**
     * 返回结果集
     */
    public Object data;

    private static String getLanguageMsg(String msg) {
        String languageMsg = LanguageUtils.getMessage(msg);
        if (StringUtils.isNotBlank(languageMsg)) {
            return languageMsg;
        } else {
            return msg;
        }
    }

    /**
     * 成功返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static String success(String code, String msg, Object data){
        CommandResult restResult=new CommandResult();
        restResult.status=true;
        restResult.code=code;
        restResult.msg=getLanguageMsg(msg);
        restResult.data=data;
        return JSON.toJSONString(restResult);
    }

    /**
     * 成功返回
     * @param msg
     * @param data
     * @return
     */
    public static String success(String msg, Object data){
        CommandResult restResult=new CommandResult();
        restResult.status=true;
        restResult.code="1";
        restResult.msg=getLanguageMsg(msg);
        restResult.data=data;
        return JSON.toJSONString(restResult);
    }

    /**
     * 成功返回
     * @param msg
     * @return
     */
    public static String success(String msg){
        CommandResult restResult=new CommandResult();
        restResult.status=true;
        restResult.code="1";
        restResult.msg=getLanguageMsg(msg);
        restResult.data=null;
        return JSON.toJSONString(restResult);
    }

    /**
     * 成功返回
     * @param data
     * @return
     */
    public static String success(Object data){
        CommandResult restResult=new CommandResult();
        restResult.status=true;
        restResult.code="1";
        restResult.msg=getLanguageMsg("success");
        restResult.data=data;
        return JSON.toJSONString(restResult);
    }

    /**
     * 失败返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static String fail(String code, String msg, Object data){
        CommandResult restResult=new CommandResult();
        restResult.status=false;
        restResult.code=code;
        restResult.msg=getLanguageMsg(msg);
        restResult.data=data;
        return JSON.toJSONString(restResult);
    }

    /**
     * 失败返回
     * @param msg
     * @param data
     * @return
     */
    public static String fail(String msg, Object data){
        CommandResult restResult=new CommandResult();
        restResult.status=false;
        restResult.code="-1";
        restResult.msg=getLanguageMsg(msg);
        restResult.data=data;
        return JSON.toJSONString(restResult);
    }

    /**
     * 失败返回
     * @param msg
     * @return
     */
    public static String fail(String msg){
        CommandResult restResult=new CommandResult();
        restResult.status=false;
        restResult.code="-1";
        restResult.msg=getLanguageMsg(msg);
        restResult.data=null;
        return JSON.toJSONString(restResult);
    }

    /**
     * 失败返回
     * @param data
     * @return
     */
    public static String fail(Object data){
        CommandResult restResult=new CommandResult();
        restResult.status=false;
        restResult.code="-1";
        restResult.msg=getLanguageMsg("fail");
        restResult.data=data;
        return JSON.toJSONString(restResult);
    }

    public static String result(boolean status){
        if(status){
            return CommandResult.success("success");
        }else {
            return CommandResult.fail("fail");
        }
    }

    public static String result(boolean status, String msg){
        if(status){
            return CommandResult.success("success");
        }else {
            return CommandResult.fail(getLanguageMsg(msg));
        }
    }

    public static String result(boolean status, Object data){
        if(status){
            return CommandResult.success(data);
        }else {
            return CommandResult.fail(data);
        }
    }

}
