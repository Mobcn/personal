package com.mobingc.personal.config.web;

import com.mobingc.personal.common.handler.AuthenticationHandler;
import com.mobingc.personal.common.utils.SpringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * 静态路径配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry
                .addResourceHandler("/upload/images/**")
                .addResourceLocations("file:upload/images/");

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册登录认证拦截器
        InterceptorRegistration registration = registry.addInterceptor(SpringUtils.getBean(AuthenticationHandler.class));
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(
                "/user/login",
                "/user/login/",
                "/article/list",
                "/article/list/",
                "/article/info/*",
                "/setting/public",
                "/setting/public/",
                "/image/download/*",
                "/webjars/**",
                "/swagger-ui.html",
                "/swagger-resources/**"
        );
    }

}
