package com.sanxin.cloud.app.api.interceptor;


import com.sanxin.cloud.common.Constant;
import com.sanxin.cloud.config.redis.RedisCacheManage;
import com.sanxin.cloud.exception.ThrowJsonException;
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
    private RedisCacheManage redisCacheManage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String regId = request.getHeader("regId");
        String token = request.getHeader("token");
        log.info("regId:" + regId);
        log.info("token:" + token);
        if (StringUtils.isEmpty(regId)) {
            this.setHead(response);
            throw new ThrowJsonException("登录过期，请重新登录");
        }
        if (StringUtils.isEmpty(token)) {
            this.setHead(response);
            throw new ThrowJsonException("登录过期，请重新登录");
        }

        String redisToken = (String) redisCacheManage.get(Constant.APP_USER_TOKEN + regId);
        if (StringUtils.isEmpty(redisToken)) {
            this.setHead(response);
            throw new ThrowJsonException("登录过期，请重新登录");
        } else {
            if (redisToken.equals(token)) {
                redisCacheManage.expire(Constant.APP_USER_TOKEN + regId, redisTokenTime);
                return true;
            } else {
                this.setHead(response);
                throw new ThrowJsonException("登录过期，请重新登录");
            }
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
