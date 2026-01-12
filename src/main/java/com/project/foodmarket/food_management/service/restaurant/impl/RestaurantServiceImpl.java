package com.project.foodmarket.food_management.service.restaurant.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.configuration.MongoContextHolder;
import com.project.foodmarket.food_management.document.Restaurant;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.restaurant.RestaurantRegisterRequest;
import com.project.foodmarket.food_management.model.restaurant.merchant.MerchantRestaurantResponse;
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
        restaurant.setOwnerId(userId);
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setSettings(request.getSettings());

        MongoContextHolder.setDatabaseName("restaurant");
        restaurantRepository.save(restaurant);
        MongoContextHolder.clear();
    }

    @Override
    public MerchantRestaurantResponse getMerchantRestaurants(User user) {
        List<Restaurant> ownerRestaurants = restaurantRepository.findAllByOwnerId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurants not found"));

        if (!ownerRestaurants.isEmpty()) {
            return MerchantRestaurantResponse.builder().restaurants(ownerRestaurants).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurants not found");
        }
    }

}
