package com.project.foodmarket.food_management.model.restaurant.merchant;

import java.util.List;

import com.project.foodmarket.food_management.document.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantRestaurantsResponse {

    private List<Restaurant> restaurants;

}
