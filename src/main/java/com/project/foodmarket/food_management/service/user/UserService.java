package com.project.foodmarket.food_management.service.user;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.user.UserRegisterRequest;
import com.project.foodmarket.food_management.model.user.UserResponse;


public interface UserService {
    public void register(@RequestBody UserRegisterRequest request);
    public UserResponse getUser(User user);
}
