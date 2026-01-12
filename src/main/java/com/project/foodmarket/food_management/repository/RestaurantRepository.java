package com.project.foodmarket.food_management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.foodmarket.food_management.document.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Optional<Restaurant> findByOwnerId(String ownerId);

    Optional<List<Restaurant>> findAllByOwnerId(String ownerId);
}
