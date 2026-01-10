package com.project.foodmarket.food_management.resolver;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.repository.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {        
        return Customer.class.equals(parameter.getParameterType());
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String bearerToken = servletRequest.getHeader("Authorization");        
        if (bearerToken == null || !bearerToken.contains("Bearer ")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String token = bearerToken.substring(7);
        Customer customer = customerRepository.findFirstByToken(token).orElseThrow( () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));     
        if (customer.getTokenExpiredAt() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return customer;
    }
    
}
