package com.project.foodmarket.food_management.document;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String name;
    
    private String ownerId;

    private Double rating;

    private Map<String, Boolean> settings;

    private Menu menu;
}
