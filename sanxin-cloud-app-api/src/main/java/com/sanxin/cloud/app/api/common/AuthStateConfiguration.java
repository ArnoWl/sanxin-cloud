package com.sanxin.cloud.app.api.common;

import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.context.annotation.Bean;

public class AuthStateConfiguration {
    @Bean
    public AuthStateCache authStateCache() {
        return new MyAuthStateCache();
    }
}
