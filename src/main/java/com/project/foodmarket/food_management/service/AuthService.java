package com.project.foodmarket.food_management.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.model.CustomerLoginRequest;
import com.project.foodmarket.food_management.model.CustomerResponse;

public interface AuthService {
    public CustomerResponse login(@RequestBody CustomerLoginRequest request);
}
