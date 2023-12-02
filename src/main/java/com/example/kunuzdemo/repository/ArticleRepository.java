package com.example.kunuzdemo.repository;

import com.example.kunuzdemo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article , UUID> {

    Optional<Article> getArticleById(UUID id);
}
