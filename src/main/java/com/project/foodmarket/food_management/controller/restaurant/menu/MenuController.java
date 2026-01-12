package com.project.foodmarket.food_management.controller.restaurant.menu;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.foodmarket.food_management.annotation.CurrentUser;
import com.project.foodmarket.food_management.constants.RestaurantConstants;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.restaurant.merchant.menu.AddMenuRequest;
import com.project.foodmarket.food_management.service.restaurant.menu.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping(path = RestaurantConstants.MERCHANT_BASE_PATH
            + "/{restaurantId}/menus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WebResponse<String> addMenu(
            @CurrentUser String userId,
            @PathVariable String restaurantId,
            @RequestPart(name = "data") AddMenuRequest request,
            @RequestPart(name = "imagefile") MultipartFile imageFile) {
        menuService.addMenu(restaurantId, request, imageFile);

        return WebResponse.<String>builder().data("OK").build();
    }
}