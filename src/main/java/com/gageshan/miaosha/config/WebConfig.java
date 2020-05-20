package com.gageshan.miaosha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Create by gageshan on 2020/5/15 18:32
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private UserResolver userResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userResolver);
    }
}
