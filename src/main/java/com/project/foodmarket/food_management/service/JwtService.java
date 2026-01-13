package com.project.foodmarket.food_management.service;

import com.project.foodmarket.food_management.document.User;

public interface JwtService {
    public String generateToken(
            String targetApplication,
            String id,
            String username,
            long tokenExpired);

    public User getUser(String token);
    public long getExpiration(String token);
}
