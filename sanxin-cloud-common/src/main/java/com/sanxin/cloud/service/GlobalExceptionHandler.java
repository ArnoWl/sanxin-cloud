package com.sanxin.cloud.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.exception.ThrowJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.InputMismatchException;


/**
 * 全局异常处理
 * @author arno
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value =  ArithmeticException.class)
    @ResponseBody
    public RestResult typeError(HttpServletRequest req, Exception e) {
		logger.info("ArithmeticException异常"+e.getMessage());
		return RestResult.fail("参数类型错误");
    }
	
	@ExceptionHandler(value =  IllegalArgumentException.class)
    @ResponseBody
    public RestResult paramsError(HttpServletRequest req,Exception e) {
		logger.info("IllegalArgumentException异常"+e.getMessage());
		return RestResult.fail("请求参数错误");
    }
	
	@ExceptionHandler(value =  ClassNotFoundException.class)
    @ResponseBody
    public RestResult classError(HttpServletRequest req,Exception e) {
		logger.info("ClassNotFoundException异常"+e.getMessage());
		return RestResult.fail("文件丢失错误");
    }
	
	@ExceptionHandler(value =  ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public RestResult arrayError(HttpServletRequest req,Exception e) {
		logger.info("ArrayIndexOutOfBoundsException异常"+e.getMessage());
		return RestResult.fail("数组溢出错误");
    }
	
	@ExceptionHandler(value =  InputMismatchException.class)
    @ResponseBody
    public RestResult inputError(HttpServletRequest req,Exception e) {
		logger.info("InputMismatchException异常"+e.getMessage());
		return RestResult.fail("接收数据类型错误");
    }
	
	@ExceptionHandler(value = {MethodArgumentTypeMismatchException.class,NumberFormatException.class} )
    @ResponseBody
    public RestResult numberError(HttpServletRequest req,Exception e) {
		logger.info("MethodArgumentTypeMismatchException,NumberFormatException异常"+e.getMessage());
		return RestResult.fail("数据格式化异常");
    }
	
	@ExceptionHandler(value = { IOException.class })  
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    public RestResult exception(Exception exception) {
		logger.info("IOException异常"+exception.getMessage());
		 return RestResult.fail("流处理异常");
    }  
	
	
	/**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(ThrowJsonException.class)
    @ResponseBody
    public RestResult handleBusinessException(ThrowJsonException e){
		switch (e.getMessage()) {
		case "997":
			return RestResult.fail("Token can't be Null",null,"997");
		case "998":
			return RestResult.fail("Logon information error",null,"998");
		case "999":
			return RestResult.fail("Unique primary can't be Null",null,"999");
		case "1000":
			return RestResult.fail("登录已过期或在其他平台登陆",null,"1000");
		default:
			return RestResult.fail(e.getMessage());
		}
    }
   
    @ExceptionHandler(value =  Exception.class)
    @ResponseBody
    public RestResult errorResponse(HttpServletRequest req,Exception e) {
		logger.info("Exception异常："+e.getMessage());
		return RestResult.fail("请求错误");
    }
}
