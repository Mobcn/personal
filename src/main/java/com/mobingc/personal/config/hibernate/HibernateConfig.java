package com.mobingc.personal.config.hibernate;

import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import java.util.Collections;

@Configuration
public class HibernateConfig {

    /**
     * 注册Hibernate配置
     */
    @Bean
    public HibernatePropertiesCustomizer registerHibernateConfig() {
        return hibernateProperties -> {
            // 注释配置
            hibernateProperties.put("hibernate.use_sql_comments", true);
            hibernateProperties.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(CommentIntegrator.INSTANCE));
        };
    }

    /**
     * 改变session的生命周期，当web请求关闭时才结束session
     */
    @Bean
    public FilterRegistrationBean<OpenEntityManagerInViewFilter> registerOpenEntityManagerInViewFilterBean() {
        FilterRegistrationBean<OpenEntityManagerInViewFilter> registrationBean = new FilterRegistrationBean<>();
        OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(5);
        return registrationBean;
    }

}
