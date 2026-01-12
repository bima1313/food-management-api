package com.project.foodmarket.food_management.service.restaurant;

import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.restaurant.RestaurantRegisterRequest;
import com.project.foodmarket.food_management.model.restaurant.merchant.MerchantRestaurantResponse;

public interface RestaurantService {
    public void register(String userId, @RequestBody RestaurantRegisterRequest request);

    public MerchantRestaurantResponse getMerchantRestaurants(User user);
}
