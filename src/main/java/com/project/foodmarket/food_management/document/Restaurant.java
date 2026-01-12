package com.project.foodmarket.food_management.document;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.project.foodmarket.food_management.model.restaurant.merchant.menu.Menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "restaurant")
public class Restaurant {
    @Id
    private String id;

    private String ownerId;

    private String name;

    private String address;    

    private Map<String, Boolean> settings;
    
    private Double rating;
    
    private List<Menu> menus;
}
