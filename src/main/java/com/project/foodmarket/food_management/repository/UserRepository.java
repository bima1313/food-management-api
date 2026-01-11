package com.project.foodmarket.food_management.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.foodmarket.food_management.document.User;

public interface UserRepository extends MongoRepository<User, String>{

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByUsername(String username);
    Optional<User> findFirstByToken(String token);
    Optional<User> findByEmail(String email);
}