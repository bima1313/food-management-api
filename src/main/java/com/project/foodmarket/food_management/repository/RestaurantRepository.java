package com.project.foodmarket.food_management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.foodmarket.food_management.document.Restaurant;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

}
