package com.project.foodmarket.food_management.service.restaurant.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.foodmarket.food_management.configuration.MongoContextHolder;
import com.project.foodmarket.food_management.document.Restaurant;
import com.project.foodmarket.food_management.model.restaurant.RestaurantRegisterRequest;
import com.project.foodmarket.food_management.repository.RestaurantRepository;
import com.project.foodmarket.food_management.service.ValidationService;
import com.project.foodmarket.food_management.service.restaurant.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    @Transactional
    public void register(String userId, RestaurantRegisterRequest request) {
        validationService.validate(request);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID().toString());
        restaurant.setName(request.getName());
        restaurant.setOwnerId(userId);
        restaurant.setRating(request.getRating());
        restaurant.setSettings(request.getSettings());

        MongoContextHolder.setDatabaseName("restaurant");
        restaurantRepository.save(restaurant);        
        MongoContextHolder.clear();
    }
    
}
