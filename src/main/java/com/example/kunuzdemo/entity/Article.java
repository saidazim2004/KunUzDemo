package com.example.kunuzdemo.entity;

import com.example.kunuzdemo.enums.ArticleStatus;
import com.example.kunuzdemo.enums.Language;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

public class Article extends BaseEntity {
    @Column(nullable = false)
    private String title ;
    private String description ;
    private UserEntity createBy ;
    private Media media ;
    private Region region ;
    private Category category ;
    private ArticleStatus articleStatus ;
    private Language language ;
    private LocalDateTime publishedDate;

    private Double viewCount = 0D;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    private boolean deleted;
}
