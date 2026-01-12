package com.project.foodmarket.food_management.model.restaurant;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantRegisterRequest {

    @NotBlank
    @Size(min = 4)
    private String name;

    @NotBlank
    @Size(min = 4)
    private String address;

    @NotEmpty
    private Map<String, Boolean> settings;   
}
