package com.project.foodmarket.food_management.service.customer;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.customer.CustomerLoginRequest;

public interface AuthService {
    public TokenResponse login(@RequestBody CustomerLoginRequest request);
    public void logout(Customer customer);
}
