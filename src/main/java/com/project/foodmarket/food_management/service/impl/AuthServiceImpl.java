package com.project.foodmarket.food_management.service.impl;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.CustomerLoginRequest;
import com.project.foodmarket.food_management.model.CustomerResponse;
import com.project.foodmarket.food_management.repository.CustomerRepository;
import com.project.foodmarket.food_management.service.AuthService;
import com.project.foodmarket.food_management.service.JwtService;
import com.project.foodmarket.food_management.service.ValidationService;

import io.jsonwebtoken.security.Keys;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private JwtService jwtService;

    @Override
    @Transactional
    public CustomerResponse login(CustomerLoginRequest request) {
        validationService.validate(request);
        Customer customer = customerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and Password was wrong"));

        if (BCrypt.checkpw(request.getPassword(), customer.getPassword())) {
            String secretString = customer.getId() + customer.getEmail() + customer.getUsername();
            SecretKey secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_16));

            String token = jwtService.generateToken(secretKey, "customer", customer.getUsername(),
                    System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);
            
            customer.setToken(token);            
            customerRepository.save(customer);

            return CustomerResponse.builder().token(token).build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and Password was wrong");
        }
    }

}
