package com.project.foodmarket.food_management.service.customer;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.customer.CustomerRegisterRequest;
import com.project.foodmarket.food_management.model.customer.CustomerResponse;


public interface CustomerService {
    public void register(@RequestBody CustomerRegisterRequest request);
    public CustomerResponse getCustomer(Customer customer);
}
