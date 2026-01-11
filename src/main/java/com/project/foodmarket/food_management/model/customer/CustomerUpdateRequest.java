package com.project.foodmarket.food_management.model.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateRequest {

    @Size(min = 4, max = 100)
        @Email(
        regexp = ".+\\.com$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Invalid email format. Only .com domains are allowed."
    )
    private String email;

    @Size(min = 8, max = 100)
    private String password;
    
    @Size(min = 4, max = 100)
    private String name;

}
