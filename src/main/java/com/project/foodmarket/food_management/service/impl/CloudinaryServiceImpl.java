package com.project.foodmarket.food_management.service.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.foodmarket.food_management.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImageFile(MultipartFile imageFile, String folderName) {
        var params = ObjectUtils.asMap(
                "folder", "images/" + folderName,
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true,
                "resource_type", "image",
                "format", "webp");
        try {
            var upload = cloudinary.uploader().upload(imageFile.getBytes(), params);
            
            return upload.get("url").toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload image failed");
        }
    }
}