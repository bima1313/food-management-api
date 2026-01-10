package com.project.foodmarket.food_management.service;

public interface JwtService {
    public String generateToken(        
        String targetApplication, 
        String customerUsername, 
        long tokenExpired);        
}
