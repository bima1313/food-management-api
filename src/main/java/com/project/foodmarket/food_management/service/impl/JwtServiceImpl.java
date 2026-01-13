package com.project.foodmarket.food_management.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.configuration.MongoContextHolder;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.repository.UserRepository;
import com.project.foodmarket.food_management.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.application.name}")
    private String secretString;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public User getUser(String token) {
        MongoContextHolder.setDatabaseName("accounts");
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_16));

        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        String userId = claims.getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        MongoContextHolder.clear();
        return user;
    }

    @Override
    public long getExpiration(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_16));

        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return claims.getExpiration().getTime();
    }

}
