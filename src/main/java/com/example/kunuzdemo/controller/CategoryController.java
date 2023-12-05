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

import java.util.List;
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
    @GetMapping("/get-by-id")
    public ResponseEntity<CategoryResponseDTO> getById(@RequestParam UUID categoryID){
        System.out.println(categoryID);
        CategoryResponseDTO categoryResponseDTO = categoryService.getById(categoryID);
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryResponseDTO>> getAll(){
        List<CategoryResponseDTO> categoryResponseDTOS = categoryService.getAll();
        return ResponseEntity.ok(categoryResponseDTOS);
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-all-visible")
    public ResponseEntity<List<CategoryResponseDTO>> getAllVisible() {
        return ResponseEntity.ok(categoryService.getAllVisible());
    }



}
