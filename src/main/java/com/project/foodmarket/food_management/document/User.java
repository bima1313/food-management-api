package com.project.foodmarket.food_management.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String email;

    private String password;

    private String username;
    
    private String name;   
    
    private String token;

    private long tokenExpiredAt;

    private String role;
}
