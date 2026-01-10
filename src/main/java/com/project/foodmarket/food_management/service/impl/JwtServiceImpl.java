package com.project.foodmarket.food_management.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.foodmarket.food_management.repository.CustomerRepository;
import com.project.foodmarket.food_management.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${app.secret.key}")
    private String secretString;

    @Override
    public String generateToken(
            String targetApplication,
            String customerUsername,
            long tokenExpired) {

        String customerId = customerRepository.findByUsername(customerUsername).getId();
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_16));
        return Jwts.builder()
                .id(customerId)
                .issuer("food-market")
                .subject(customerUsername)
                .audience().add(targetApplication).and()
                .expiration(new Date(tokenExpired))
                .notBefore(new Date(System.currentTimeMillis()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(key).compact();
    }

}
