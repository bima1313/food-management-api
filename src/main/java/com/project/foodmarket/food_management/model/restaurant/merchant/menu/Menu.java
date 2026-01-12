package com.project.foodmarket.food_management.model.restaurant.merchant.menu;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Menu {
    @Id
    private String id;

    private String name;

    private Integer price;

    private String category;

    private String imageUrl;
}
