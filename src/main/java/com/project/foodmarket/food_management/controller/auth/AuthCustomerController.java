package com.project.foodmarket.food_management.controller.auth;

import org.springframework.web.bind.annotation.RestController;

import com.project.foodmarket.food_management.constants.CustomerConstants;
import com.project.foodmarket.food_management.model.CustomerLoginRequest;
import com.project.foodmarket.food_management.model.CustomerResponse;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthCustomerController {

    @Autowired
    private AuthService authService;

    @PostMapping(
        path = CustomerConstants.LOGIN_PATH, 
        produces = MediaType.APPLICATION_JSON_VALUE, 
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CustomerResponse> login(@RequestBody CustomerLoginRequest request) {        
        CustomerResponse token = authService.login(request);
        
        return WebResponse.<CustomerResponse>builder().data(token).build();
    }
    
}
