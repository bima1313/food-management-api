package com.project.foodmarket.food_management.service.user;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.user.UserLoginRequest;
import com.project.foodmarket.food_management.model.user.UserResponse;
import com.project.foodmarket.food_management.model.user.UserUpdateRequest;

public interface AuthService {
    public TokenResponse login(@RequestBody UserLoginRequest request);

    public void logout(User user);

    public UserResponse update(User user, @RequestBody UserUpdateRequest request);

    public void deleteAccountUser(User user);
}
