package com.project.foodmarket.food_management.service.restaurant;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.model.restaurant.RestaurantRegisterRequest;

public interface RestaurantService {
    public void register(String userId, @RequestBody  RestaurantRegisterRequest request);
}
