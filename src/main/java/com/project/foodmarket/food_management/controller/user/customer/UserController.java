package com.project.foodmarket.food_management.controller.user.customer;

import org.springframework.web.bind.annotation.RestController;

import com.project.foodmarket.food_management.annotation.CurrentUser;
import com.project.foodmarket.food_management.constants.CustomerConstants;
import com.project.foodmarket.food_management.constants.RoleConstants;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.enums.RoleEnum;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.user.UserRegisterRequest;
import com.project.foodmarket.food_management.model.user.UserResponse;
import com.project.foodmarket.food_management.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = CustomerConstants.REGISTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody UserRegisterRequest request) {
        userService.register(request, RoleEnum.CUSTOMER);

        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = CustomerConstants.GET_USER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(RoleConstants.CustomerRole)
    public WebResponse<UserResponse> get(@CurrentUser User user) {
        UserResponse userResponse = userService.getUser(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

}
