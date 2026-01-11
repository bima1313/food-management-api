package com.project.foodmarket.food_management.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.foodmarket.food_management.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.application.name}")
    private String secretString;

    @Override
    public String generateToken(
            String targetApplication,
            String id,
            String username,
            long tokenExpired) {
        
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_16));
        return Jwts.builder()
                .id(id)
                .issuer("food-market")
                .subject(username)
                .audience().add(targetApplication).and()
                .expiration(new Date(tokenExpired))
                .notBefore(new Date(System.currentTimeMillis()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(key).compact();
    }

}
