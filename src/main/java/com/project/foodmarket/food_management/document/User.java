package com.project.foodmarket.food_management.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.project.foodmarket.food_management.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String email;

    private String password;

    private String username;
    
    private String name;   
    
    private String token;

    private long tokenExpiredAt;

    private RoleEnum role;
}
