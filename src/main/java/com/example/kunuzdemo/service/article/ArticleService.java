package com.example.kunuzdemo.service.article;

import com.example.kunuzdemo.dtos.request.ArticleCreateDto;
import com.example.kunuzdemo.dtos.response.ArticleResponseDto;

public interface ArticleService {
    ArticleResponseDto create(ArticleCreateDto createDTO);
}
