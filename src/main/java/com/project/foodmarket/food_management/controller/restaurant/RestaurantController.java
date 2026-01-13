package com.project.foodmarket.food_management.controller.restaurant;

import org.springframework.web.bind.annotation.RestController;

import com.project.foodmarket.food_management.annotation.CurrentUser;
import com.project.foodmarket.food_management.constants.RestaurantConstants;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.restaurant.MerchantRestaurantResponse;
import com.project.foodmarket.food_management.model.restaurant.RestaurantRegisterRequest;
import com.project.foodmarket.food_management.model.restaurant.merchant.MerchantRestaurantsResponse;
import com.project.foodmarket.food_management.service.restaurant.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(path = RestaurantConstants.REGISTER_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@CurrentUser User user, @RequestBody RestaurantRegisterRequest request) {
        restaurantService.register(user, request);

        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = RestaurantConstants.MERCHANT_BASE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MerchantRestaurantsResponse> getMerchantRestaurants(@CurrentUser User user) {
        MerchantRestaurantsResponse merchantRestaurants = restaurantService.getMerchantRestaurants(user);

        return WebResponse.<MerchantRestaurantsResponse>builder().data(merchantRestaurants).build();
    }

    @GetMapping(path = RestaurantConstants.MERCHANT_BASE_PATH + "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MerchantRestaurantResponse> getMerchantRestaurant(@PathVariable String restaurantId) {
        MerchantRestaurantResponse merchantRestaurant = restaurantService.getMerchantRestaurant(restaurantId);

        return WebResponse.<MerchantRestaurantResponse>builder().data(merchantRestaurant).build();
    }

    
}
