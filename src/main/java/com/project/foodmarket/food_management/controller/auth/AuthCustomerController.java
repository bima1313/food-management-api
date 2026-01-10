package com.project.foodmarket.food_management.controller.auth;

import org.springframework.web.bind.annotation.RestController;

import com.project.foodmarket.food_management.constants.CustomerConstants;
import com.project.foodmarket.food_management.model.CustomerLoginRequest;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthCustomerController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = CustomerConstants.LOGIN_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<TokenResponse>> login(@RequestBody CustomerLoginRequest request) {

        TokenResponse tokenResponse = authService.login(request);
        WebResponse<TokenResponse> body = WebResponse.<TokenResponse>builder().data(tokenResponse).build();
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION).body(body);
    }

}
