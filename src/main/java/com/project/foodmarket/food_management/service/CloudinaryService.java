package com.project.foodmarket.food_management.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String uploadImageFile(MultipartFile imageFile, String folderName);
}
