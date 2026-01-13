package com.project.foodmarket.food_management.service.user.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.configuration.MongoContextHolder;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.user.UserLoginRequest;
import com.project.foodmarket.food_management.model.user.UserResponse;
import com.project.foodmarket.food_management.model.user.UserUpdateRequest;
import com.project.foodmarket.food_management.repository.UserRepository;
import com.project.foodmarket.food_management.service.JwtService;
import com.project.foodmarket.food_management.service.ValidationService;
import com.project.foodmarket.food_management.service.user.AuthService;
import com.project.foodmarket.utils.ExpirationUtils;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private JwtService jwtService;

    @Override
    @Transactional
    public TokenResponse login(UserLoginRequest request) {
        validationService.validate(request);
        MongoContextHolder.setDatabaseName("accounts");

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and Password was wrong"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {

            String token = jwtService.generateToken("user", user.getId(), user.getUsername(),
                    ExpirationUtils.calculateTokenExpiredDate(7));

            user.setToken(token);
            user.setTokenExpiredAt(ExpirationUtils.calculateTokenExpiredDate(7));
            userRepository.save(user);

            MongoContextHolder.clear();
            return TokenResponse.builder()
                    .token(user.getToken())
                    .tokenExpiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and Password was wrong");
        }
    }

    @Override
    public void logout(User user) {
        MongoContextHolder.setDatabaseName("accounts");
        user.setToken(null);
        user.setTokenExpiredAt(0);

        userRepository.save(user);
        MongoContextHolder.clear();
    }

    @Override
    public UserResponse update(User user, UserUpdateRequest request) {
        validationService.validate(request);
        MongoContextHolder.setDatabaseName("accounts");

        if (Objects.nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }
        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        userRepository.save(user);
        MongoContextHolder.clear();
        return UserResponse
                .builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    @Override
    public void deleteAccountUser(User user) {
        MongoContextHolder.setDatabaseName("accounts");
        userRepository.delete(user);
        MongoContextHolder.clear();
    }
}
