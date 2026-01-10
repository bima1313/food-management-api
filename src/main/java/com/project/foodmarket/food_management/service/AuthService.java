package com.project.foodmarket.food_management.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.model.CustomerLoginRequest;
import com.project.foodmarket.food_management.model.TokenResponse;

public interface AuthService {
    public TokenResponse login(@RequestBody CustomerLoginRequest request);
}
