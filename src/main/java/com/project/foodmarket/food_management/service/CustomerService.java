package com.project.foodmarket.food_management.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.CustomerRegisterRequest;
import com.project.foodmarket.food_management.model.CustomerResponse;


public interface CustomerService {
    public void register(@RequestBody CustomerRegisterRequest request);
    public CustomerResponse getCustomer(Customer customer);
}
