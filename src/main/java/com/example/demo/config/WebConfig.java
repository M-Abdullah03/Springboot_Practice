package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.example.demo.filter.RequestLoggingFilter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilterRegistration () {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<RequestLoggingFilter>();

        registrationBean.setFilter(new RequestLoggingFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
