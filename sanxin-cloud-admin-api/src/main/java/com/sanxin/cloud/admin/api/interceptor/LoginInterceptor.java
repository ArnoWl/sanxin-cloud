package com.sanxin.cloud.admin.api.interceptor;

import com.sanxin.cloud.admin.api.service.LoginService;
import com.sanxin.cloud.admin.api.service.UtilService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysMenus;
import com.sanxin.cloud.exception.LoginOutException;
import com.sanxin.cloud.exception.ThrowJsonException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-26
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {



    @Autowired
    private LoginService loginService;
    @Autowired
    private UtilService utilService;
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
            //检验token
            loginService.getUserInfo(token);

            //获取请求路径
            String returnUrl = String.valueOf(request.getServletPath());
            List<SysMenus> list=utilService.getMenus(token);
            if(list!=null && list.size()>0){
                List<String> strs=new ArrayList<>();
                for(SysMenus l:list){
                    for (SysMenus c:l.getChildList()){
                        strs.add(c.getUrl());
                    }
                }
                System.out.println("str：：：："+strs);
                strs.add("/user/getInfo");
                strs.add("/role/queryMyroleMenus");
                strs.add("/role/querySysUserList");
                strs.add("/role/queryRoleList");
                strs.add("/role/queryMenus");
                strs.add("/advert/list");
                strs.add("/business/list");
                strs.add("/business/queryAllList");
                strs.add("/agent/list");
                strs.add("/system/queryAgreementList");
                strs.add("/uploadOne");
                strs.add("/system/queryGuideList");
                strs.add("/system/getAboutUs");
                strs.add("/cash/getCustomerCashRule");
                strs.add("/cash/getBusinessCashRule");
                strs.add("/cash/queryCashList");
                strs.add("/cash/queryBankTypeList");
                strs.add("/advert/queryAdvertContentList");
                strs.add("/advert/getAdvertContentDetail");
                strs.add("/queryEventType");
                strs.add("/system/queryGiftHourList");
                strs.add("/device/queryDeviceList");
                strs.add("/device/getDeviceDetail");
                strs.add("/payment/getAliPayDetail");
                strs.add("/payment/editAliPay");
                strs.add("/home/labelMsg");
                strs.add("/home/queryCashStatistics");
                strs.add("/system/importTerminalSn");
                strs.add("/system/feedbackList");
                strs.add("/customer/amountDetails");
                strs.add("/customer/depositDetails");
                strs.add("/customer/timeDetails");
                strs.add("/order/list");
                strs.add("/order/detail");
                System.out.println("returnUrl：："+returnUrl);
                boolean bool = strs.contains(returnUrl);
                if(!bool) {
                    throw new ThrowJsonException("You do not have this permission");
                }
            }
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
