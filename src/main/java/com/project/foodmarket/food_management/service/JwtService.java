package com.project.foodmarket.food_management.service;

public interface JwtService {
    public String generateToken(
            String targetApplication,
            String id,
            String username,
            long tokenExpired);

    public String getUserId(String token);
    public long getExpiration(String token);
}
