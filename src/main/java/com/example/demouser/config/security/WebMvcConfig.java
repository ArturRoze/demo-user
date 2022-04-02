package com.example.demouser.config.security;

import com.example.demouser.rest.interseptor.UserRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserRequestInterceptor userRequestInterceptor;

    @Autowired
    public WebMvcConfig(UserRequestInterceptor userRequestInterceptor) {
        this.userRequestInterceptor = userRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userRequestInterceptor);
    }
}
