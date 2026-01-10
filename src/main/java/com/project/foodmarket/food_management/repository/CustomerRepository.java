package com.project.foodmarket.food_management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.foodmarket.food_management.document.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    UUID findByUsername(String username);
    Optional<Customer> findByEmail(String email);
}