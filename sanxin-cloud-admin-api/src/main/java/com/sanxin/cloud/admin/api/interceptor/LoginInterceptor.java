package com.sanxin.cloud.admin.api.interceptor;

import com.sanxin.cloud.admin.api.service.LoginService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.exception.ThrowJsonException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-26
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {



    @Autowired
    private LoginService loginService;
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true  s
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("sanxinToken");
        if(StringUtils.isEmpty(token)){
            throw new ThrowJsonException("Your landing has expired. Please re-login.");
        }else {
            RestResult result=loginService.getUserInfo(token);
            if(!result.status){
                throw new ThrowJsonException("You cannot operate without this function permission");
            }
            if("1001".equals(result.code)){
                throw new ThrowJsonException("You cannot operate without this function permission");
            }
            //获取请求路径
//            String returnUrl = String.valueOf(request.getServletPath());
//            List<String> strs=PlatSession.getMenuUrlList(request);
//
//            boolean bool = strs.contains(returnUrl);
//            if(!bool) {
//                throw new ThrowJsonException("You cannot operate without this function permission");
//            }
        }
        return true;
    }
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
