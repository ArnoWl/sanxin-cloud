package com.sanxin.cloud.app.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author zdc
 * @date 2019/9/7 11:36
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    TokenInterceptor tokenInterceptor;

    /**
     * 配置跨域问题
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3600)
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                // 拦截所有请求
                .addPathPatterns("/**")
                .excludePathPatterns("/user-protocol/**")
                .excludePathPatterns("/register/**")
                .excludePathPatterns("/richText/**")
                .excludePathPatterns("/agree/**")
                .excludePathPatterns("/business/**")
                .excludePathPatterns("/base/**")
                //第三方登录
                .excludePathPatterns("/register/tripartiteLogin")
                //第三方绑定
                .excludePathPatterns("/register/bindingPhone")
                //首页广告
                .excludePathPatterns("/message/queryHomeAdvert")
                //发现广告
                .excludePathPatterns("/message/queryAdvertList")
                //借充电宝中间提示
                .excludePathPatterns("/order/powerBankPrompt")
                // 排除swagger相关请求
                .excludePathPatterns("/webjars/**", "/v2/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
