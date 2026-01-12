package com.project.foodmarket.food_management.service.restaurant.menu;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.project.foodmarket.food_management.model.restaurant.merchant.menu.AddMenuRequest;

public interface MenuService {
    public void addMenu(@PathVariable String restaurantId,
            @RequestPart(name = "data") AddMenuRequest request,
            @RequestPart(name = "imagefile") MultipartFile imageFile);
}
