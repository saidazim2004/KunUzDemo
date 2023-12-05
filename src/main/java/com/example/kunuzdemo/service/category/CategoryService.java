package com.example.kunuzdemo.service.category;

import com.example.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.example.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.example.kunuzdemo.entity.Category;

import java.util.UUID;

public interface CategoryService {
    Category getCategory(UUID categoryID);

    CategoryResponseDTO create(CategoryCreateDTO dto);

    CategoryResponseDTO getById(UUID categoryID);

}
