package com.example.kunuzdemo.service.article;

import com.example.kunuzdemo.dtos.request.ArticleCreateDto;
import com.example.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.example.kunuzdemo.dtos.response.ArticleResponseDto;
import com.example.kunuzdemo.entity.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    ArticleResponseDto create(ArticleCreateDto createDTO);

    ArticleResponseDto getById(UUID articleId);

    List<ArticleResponseDto> getByLanguage(String language, Integer page, Integer size);

    List<ArticleResponseDto> findRecommendedArticles(Integer page, Integer size);

    List<ArticleResponseDto> getByTitle(String titleName, Integer page, Integer size);

    ArticleResponseDto changeArticleStatus(UUID articleId);

    List<ArticleResponseDto> getByPublisher(Integer page, Integer size);


    List<ArticleResponseDto> getAllBlocked(Integer page, Integer size);

    List<ArticleResponseDto> getByRegionId(UUID regionID, Integer page, Integer size);

    List<ArticleResponseDto> getLatestNews(Integer page, Integer size);

    ArticleResponseDto updateById(UUID articleID, ArticleUpdateDTO updateDTO);

    String deleteById(UUID articleID);

    String deleteSelected(List<UUID> articleIDs);

    Article findById(UUID id);

}
