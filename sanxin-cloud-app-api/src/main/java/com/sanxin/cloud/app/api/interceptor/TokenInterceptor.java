package com.sanxin.cloud.app.api.interceptor;


import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import com.sanxin.cloud.exception.LoginOutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问token拦截
 *
 * @author zdc
 * @date 2019/9/7 11:43
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Value("${spring.redis.token.time}")
    private long redisTokenTime;

    @Autowired
    private LoginTokenService loginTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String token = BaseUtil.getUserToken();
        if (StringUtils.isEmpty(token)) {
            this.setHead(response);
            throw new LoginOutException("登录过期，请重新登录");
        }

        Integer tid = loginTokenService.validLoginTid(token);
        if (tid != null) {
            return true;
        } else {
            return false;
        }
    }

    private void setHead(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.debug("处理请求完成后视图渲染之前的的处理操作");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.debug("视图渲染之后操作");
    }
}
