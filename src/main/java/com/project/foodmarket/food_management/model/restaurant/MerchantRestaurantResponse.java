package com.project.foodmarket.food_management.model.restaurant;

import com.project.foodmarket.food_management.document.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantRestaurantResponse {
    private Restaurant restaurant;
}
