package com.project.foodmarket.food_management.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.configuration.MongoContextHolder;
import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.CustomerRegisterRequest;
import com.project.foodmarket.food_management.model.CustomerResponse;
import com.project.foodmarket.food_management.repository.CustomerRepository;
import com.project.foodmarket.food_management.service.CustomerService;
import com.project.foodmarket.food_management.service.ValidationService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    @Transactional
    public void register(CustomerRegisterRequest request) {
        validationService.validate(request);

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        } else if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setEmail(request.getEmail());
        customer.setUsername(request.getUsername());
        customer.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        customer.setName(request.getName());

        MongoContextHolder.setDatabaseName("account");
        userRepository.save(customer);
        MongoContextHolder.clear();
    }

    @Override
    public CustomerResponse getCustomer(Customer customer) {
        return CustomerResponse.builder()
        .email(customer.getEmail())
        .username(customer.getUsername())
        .name(customer.getName())
        .build();
    }
}
