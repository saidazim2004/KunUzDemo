package com.example.kunuzdemo.service.article;

import com.example.kunuzdemo.dtos.request.ArticleCreateDto;
import com.example.kunuzdemo.dtos.response.ArticleResponseDto;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    ArticleResponseDto create(ArticleCreateDto createDTO);

    ArticleResponseDto getById(UUID articleId);

    List<ArticleResponseDto> getByLanguage(String language, Integer page, Integer size);

    List<ArticleResponseDto> findRecommendedArticles(Integer page, Integer size);

    List<ArticleResponseDto> getByTitle(String titleName, Integer page, Integer size);

    ArticleResponseDto changeArticleStatus(UUID articleId);

}
