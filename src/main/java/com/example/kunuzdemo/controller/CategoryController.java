package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.example.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.example.kunuzdemo.service.category.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryResponseDTO categoryResponseDTO = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/get-by-id{categoryID}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID categoryID){
        CategoryResponseDTO categoryResponseDTO = categoryService.getById(categoryID);
        return ResponseEntity.ok(categoryResponseDTO);
    }
}
