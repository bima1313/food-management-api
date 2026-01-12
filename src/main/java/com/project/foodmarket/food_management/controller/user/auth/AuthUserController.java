package com.project.foodmarket.food_management.controller.user.auth;

import org.springframework.web.bind.annotation.RestController;

import com.project.foodmarket.food_management.constants.UserConstants;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.user.UserLoginRequest;
import com.project.foodmarket.food_management.model.user.UserResponse;
import com.project.foodmarket.food_management.model.user.UserUpdateRequest;
import com.project.foodmarket.food_management.service.user.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
public class AuthUserController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = UserConstants.LOGIN_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<TokenResponse>> login(@RequestBody UserLoginRequest request) {

        TokenResponse tokenResponse = authService.login(request);
        WebResponse<TokenResponse> body = WebResponse.<TokenResponse>builder().data(tokenResponse).build();
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION).body(body);
    }

    @PatchMapping(path = UserConstants.UPDATE_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> update(User user, @RequestBody UserUpdateRequest request) {
        UserResponse updateResponse = authService.update(user, request);

        return WebResponse.<UserResponse>builder().data(updateResponse).build();
    }

    @DeleteMapping(path = UserConstants.LOGOUT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> Logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder().data("OK").build();
    }

    @DeleteMapping(path = UserConstants.DELETE_ACCOUNT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteAccountUser(User user) {
        authService.deleteAccountUser(user);
        return WebResponse.<String>builder().data("OK").build();
    }

}
