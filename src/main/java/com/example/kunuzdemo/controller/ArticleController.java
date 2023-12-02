package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.ArticleCreateDto;
import com.example.kunuzdemo.dtos.response.ArticleResponseDto;
import com.example.kunuzdemo.service.article.ArticleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService ;


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'PUBLISHER')")
    @PostMapping("/create-article")
    public ResponseEntity<ArticleResponseDto> create(
            @Valid @RequestBody ArticleCreateDto createDTO
    ) {
        ArticleResponseDto articleResponseDTO = articleService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDTO);
    }

}