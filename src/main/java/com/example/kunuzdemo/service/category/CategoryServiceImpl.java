package com.example.kunuzdemo.service.category;

import com.example.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.example.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.example.kunuzdemo.entity.Category;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository ;
    private final ModelMapper modelMapper ;

    @Override
    public CategoryResponseDTO create(CategoryCreateDTO dto) {
        Category category = modelMapper.map(dto, Category.class);
        Category saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryResponseDTO.class);
    }

    @Override
    public Category getCategory(UUID categoryID) {
        return getCategoryById(categoryID);
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(
                () -> new DataNotFoundException("category not found with ID :" + categoryId));
    }
}