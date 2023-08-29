package com.heima.search.config;

import com.heima.search.interceptor.AppTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.search.config
 * @CLASS_NAME: WebMvcConfig
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/20 22:02
 * @Emial: 1299694047@qq.com
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AppTokenInterceptor()).addPathPatterns("/**");
    }
}
