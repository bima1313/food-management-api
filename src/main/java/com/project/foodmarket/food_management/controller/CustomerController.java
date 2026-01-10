package com.project.foodmarket.food_management.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.foodmarket.food_management.constants.CustomerConstants;
import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.CustomerRegisterRequest;
import com.project.foodmarket.food_management.model.CustomerResponse;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = CustomerConstants.BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody CustomerRegisterRequest request) {
        customerService.register(request);

        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = CustomerConstants.BASE_PATH + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CustomerResponse> get(@PathVariable String id, Customer customer) {
        CustomerResponse customerResponse = customerService.getCustomer(customer);
        return WebResponse.<CustomerResponse>builder().data(customerResponse).build();
    }

}
