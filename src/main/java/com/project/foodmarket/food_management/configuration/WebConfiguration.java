package com.project.foodmarket.food_management.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.foodmarket.food_management.resolver.CustomerArgumentResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    private CustomerArgumentResolver customerArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(customerArgumentResolver);
    }
}