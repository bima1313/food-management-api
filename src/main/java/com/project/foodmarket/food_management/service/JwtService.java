package com.project.foodmarket.food_management.service;

import javax.crypto.SecretKey;

public interface JwtService {
    public String generateToken(
        SecretKey key, 
        String targetApplication, 
        String customerUsername, 
        long tokenExpired);    
}
