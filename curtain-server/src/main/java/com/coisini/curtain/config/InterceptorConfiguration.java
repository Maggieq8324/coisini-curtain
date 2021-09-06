package com.coisini.curtain.config;

import com.coisini.curtain.core.interceptors.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 拦截器注册
 * @author coisini
 * @date Aug 19, 2021
 * @Version 1.0
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPermissionInterceptor());
    }
}
