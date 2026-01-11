package com.project.foodmarket.food_management.service.customer.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.configuration.MongoContextHolder;
import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.customer.CustomerLoginRequest;
import com.project.foodmarket.food_management.model.customer.CustomerResponse;
import com.project.foodmarket.food_management.model.customer.CustomerUpdateRequest;
import com.project.foodmarket.food_management.repository.CustomerRepository;
import com.project.foodmarket.food_management.service.JwtService;
import com.project.foodmarket.food_management.service.ValidationService;
import com.project.foodmarket.food_management.service.customer.AuthService;
import com.project.foodmarket.utils.ExpirationUtils;

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
    public TokenResponse login(CustomerLoginRequest request) {
        validationService.validate(request);
        MongoContextHolder.setDatabaseName("account");

        Customer customer = customerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and Password was wrong"));

        if (BCrypt.checkpw(request.getPassword(), customer.getPassword())) {

            String token = jwtService.generateToken("customer", customer.getId(), customer.getUsername(),
                    ExpirationUtils.calculateTokenExpiredDate(7));

            customer.setToken(token);
            customer.setTokenExpiredAt(ExpirationUtils.calculateTokenExpiredDate(7));
            customerRepository.save(customer);

            MongoContextHolder.clear();
            return TokenResponse.builder()
                    .token(customer.getToken())
                    .tokenExpiredAt(customer.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and Password was wrong");
        }
    }

    @Override
    public void logout(Customer customer) {
        customer.setToken(null);
        customer.setTokenExpiredAt(0);

        customerRepository.save(customer);
        MongoContextHolder.clear();
    }

    @Override
    public CustomerResponse update(Customer customer, CustomerUpdateRequest request) {
        validationService.validate(request);

        if (Objects.nonNull(request.getEmail())) {
            customer.setEmail(request.getEmail());
        }
        if (Objects.nonNull(request.getPassword())) {
            customer.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }
        if (Objects.nonNull(request.getName())) {
            customer.setName(request.getName());
        }

        customerRepository.save(customer);
        MongoContextHolder.clear();
        return CustomerResponse
                .builder()
                .email(customer.getEmail())
                .username(customer.getUsername())
                .name(customer.getName())
                .build();
    }

    @Override
    public void deleteAccountCustomer(Customer customer) {
        customerRepository.delete(customer);
        MongoContextHolder.clear();
    }


}
