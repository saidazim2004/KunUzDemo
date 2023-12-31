package com.example.kunuzdemo.service.category;

import com.example.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.example.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.example.kunuzdemo.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category getCategory(UUID categoryID);

    CategoryResponseDTO create(CategoryCreateDTO dto);

    CategoryResponseDTO getById(UUID categoryID);

    List<CategoryResponseDTO> getAll();

    List<CategoryResponseDTO> getAllVisible();

    List<CategoryResponseDTO> getAllUnVisible();

    void deleteById(UUID categoryId);

    void deleteSelectedCategories(List<UUID> categoryIDs);

}
