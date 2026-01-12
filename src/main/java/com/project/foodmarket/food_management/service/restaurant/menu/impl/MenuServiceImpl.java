package com.project.foodmarket.food_management.service.restaurant.menu.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.project.foodmarket.food_management.configuration.MongoContextHolder;
import com.project.foodmarket.food_management.document.Restaurant;
import com.project.foodmarket.food_management.model.restaurant.merchant.menu.AddMenuRequest;
import com.project.foodmarket.food_management.model.restaurant.merchant.menu.Menu;
import com.project.foodmarket.food_management.repository.RestaurantRepository;
import com.project.foodmarket.food_management.service.CloudinaryService;
import com.project.foodmarket.food_management.service.ValidationService;
import com.project.foodmarket.food_management.service.restaurant.menu.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public void addMenu(String restaurantId, AddMenuRequest request, MultipartFile imageFile) {
        validationService.validate(request);
        MongoContextHolder.setDatabaseName("restaurant");
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        String imageUrl = cloudinaryService.uploadImageFile(imageFile, request.getCategory());
        Menu menu = new Menu();
        menu.setId(UUID.randomUUID().toString());
        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setCategory(request.getCategory());
        menu.setImageUrl(imageUrl);

        if (restaurant.getMenus() == null || restaurant.getMenus().isEmpty()) {
            List<Menu> newMenu = new ArrayList<Menu>();
            newMenu.add(menu);
            restaurant.setMenus(newMenu);

            restaurantRepository.save(restaurant);
            MongoContextHolder.clear();
        } else {
            List<Menu> allMenus = restaurant.getMenus();
            allMenus.addLast(menu);
            restaurant.setMenus(allMenus);

            restaurantRepository.save(restaurant);
            MongoContextHolder.clear();
        }
    }
}
