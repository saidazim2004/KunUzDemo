package com.example.kunuzdemo.service.category;

import com.example.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.example.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.example.kunuzdemo.entity.Category;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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


    @Override
    public CategoryResponseDTO getById(UUID categoryID) {
        return modelMapper.map(getCategoryById(categoryID) , CategoryResponseDTO.class);
    }


    @Override
    public List<CategoryResponseDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return modelMapper.map(categories , new TypeToken<List<CategoryResponseDTO>>(){}.getType());

    }




    @Override
    public List<CategoryResponseDTO> getAllVisible() {
        List<Category> categoryList = categoryRepository.findAllVisible();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {}.getType());
    }


    @Override
    public List<CategoryResponseDTO> getAllUnVisible() {
        List<Category> categoryList = categoryRepository.findAllUnVisible();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {}.getType());
    }




    @Override
    public void deleteById(UUID categoryId) {
        System.out.println(categoryId+"netdan keirib kevoti");
        Optional<Category> byId = categoryRepository.findById(categoryId);
        System.out.println("categoryId = " + byId.get().getId());
        if (byId.isEmpty()){
            throw new DataNotFoundException("category not found with ID :" + categoryId);
        }
        else {
            categoryRepository.deleteById(categoryId);

        }
    }
    @Override
    public void deleteSelectedCategories(List<UUID> categoryIDs) {
        for (UUID categoryID : categoryIDs) {
            deleteById(categoryID);
        }
    }
}
