package com.project.foodmarket.food_management.constants;

public class CustomerConstants {    
    private static final String BASE_PATH = "/api/users";   
    public static final String REGISTER_PATH = BASE_PATH + "/register";
    public static final String GET_USER_PATH = BASE_PATH + "/profile";
    public static final String UPDATE_PATH = GET_USER_PATH;    

    private static final String AUTH_PATH = BASE_PATH + "/auth";
    public static final String LOGIN_PATH = AUTH_PATH + "/login";    
    public static final String LOGOUT_PATH = AUTH_PATH + "/logout";
    public static final String DELETE_ACCOUNT_PATH = BASE_PATH + "/me";
}
