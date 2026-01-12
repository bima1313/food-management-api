package com.project.foodmarket.food_management.model.restaurant.merchant.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMenuRequest {

    @NotBlank
    @Size(min = 4)
    private String name;

    @NotNull
    private Integer price;

    @NotBlank
    @Size(min = 4)
    private String category;

}
