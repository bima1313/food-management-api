package com.project.foodmarket.food_management.service.impl;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foodmarket.food_management.repository.CustomerRepository;
import com.project.foodmarket.food_management.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String generateToken(
        SecretKey key,
        String targetApplication, 
        String customerUsername, 
        long tokenExpired) {

        String customerId = customerRepository.findByUsername(customerUsername).getId();

        return Jwts.builder()
                .id(customerId)
                .issuer("food-market")
                .subject(customerUsername)
                .audience().add(targetApplication).and()
                .expiration(new Date(tokenExpired))
                .notBefore(new Date(System.currentTimeMillis()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

}
