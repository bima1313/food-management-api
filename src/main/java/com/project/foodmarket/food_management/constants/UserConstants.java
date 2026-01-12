package com.project.foodmarket.food_management.constants;

public class UserConstants {    
    private static final String BASE_PATH = "/api/users";   
    public static final String REGISTER_PATH = BASE_PATH + "/register";
    public static final String GET_USER_PATH = BASE_PATH + "/profile";
    public static final String UPDATE_PATH = GET_USER_PATH;    

    private static final String AUTH_PATH = "/api/auth";
    public static final String LOGIN_PATH = AUTH_PATH + "/users/login";    
    public static final String LOGOUT_PATH = AUTH_PATH + "/logout";
    public static final String DELETE_ACCOUNT_PATH = AUTH_PATH;
}
