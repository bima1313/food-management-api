package com.project.foodmarket.food_management.service.restaurant;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.restaurant.MerchantRestaurantResponse;
import com.project.foodmarket.food_management.model.restaurant.RestaurantRegisterRequest;
import com.project.foodmarket.food_management.model.restaurant.merchant.MerchantRestaurantsResponse;

public interface RestaurantService {
    public void register(User user, @RequestBody RestaurantRegisterRequest request);

    public MerchantRestaurantsResponse getMerchantRestaurants(User user);

    public MerchantRestaurantResponse getMerchantRestaurant(@PathVariable String restaurantId);
}
