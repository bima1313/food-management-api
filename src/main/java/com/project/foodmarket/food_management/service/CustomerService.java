package com.project.foodmarket.food_management.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.model.CustomerRegisterRequest;


public interface CustomerService {
    public void register(@RequestBody CustomerRegisterRequest request);
}
