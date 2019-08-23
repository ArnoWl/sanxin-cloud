package com.sanxin.cloud.common.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-21
 */
@NoArgsConstructor
@Data
public class RestResult implements Serializable {


    /**
     * 请求是否成功
     * true成功 false 失败
     */
    public boolean status;

    /**
     * 状态码 00表示成功  -1表示失败
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


    /**
     * 成功返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static RestResult success(String code,String msg,Object data){
        RestResult restResult=new RestResult();
        restResult.status=true;
        restResult.code=code;
        restResult.msg=msg;
        restResult.data=data;
        return restResult;
    }

    /**
     * 成功返回
     * @param msg
     * @param data
     * @return
     */
    public static RestResult success(String msg,Object data){
        RestResult restResult=new RestResult();
        restResult.status=true;
        restResult.code="00";
        restResult.msg=msg;
        restResult.data=data;
        return restResult;
    }

    /**
     * 成功返回
     * @param msg
     * @return
     */
    public static RestResult success(String msg){
        RestResult restResult=new RestResult();
        restResult.status=true;
        restResult.code="00";
        restResult.msg=msg;
        restResult.data=null;
        return restResult;
    }

    /**
     * 成功返回
     * @param data
     * @return
     */
    public static RestResult success(Object data){
        RestResult restResult=new RestResult();
        restResult.status=true;
        restResult.code="00";
        restResult.msg="Success";
        restResult.data=data;
        return restResult;
    }

    /**
     * 失败返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static RestResult fail(String code,String msg,Object data){
        RestResult restResult=new RestResult();
        restResult.status=false;
        restResult.code=code;
        restResult.msg=msg;
        restResult.data=data;
        return restResult;
    }

    /**
     * 失败返回
     * @param msg
     * @param data
     * @return
     */
    public static RestResult fail(String msg,Object data){
        RestResult restResult=new RestResult();
        restResult.status=false;
        restResult.code="-1";
        restResult.msg=msg;
        restResult.data=data;
        return restResult;
    }

    /**
     * 成功返回
     * @param msg
     * @return
     */
    public static RestResult fail(String msg){
        RestResult restResult=new RestResult();
        restResult.status=false;
        restResult.code="-1";
        restResult.msg=msg;
        restResult.data=null;
        return restResult;
    }

    /**
     * 失败返回
     * @param data
     * @return
     */
    public static RestResult fail(Object data){
        RestResult restResult=new RestResult();
        restResult.status=false;
        restResult.code="-1";
        restResult.msg="Fail";
        restResult.data=data;
        return restResult;
    }




}
