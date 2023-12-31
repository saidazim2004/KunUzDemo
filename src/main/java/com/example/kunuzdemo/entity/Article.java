package com.example.kunuzdemo.entity;

import com.example.kunuzdemo.enums.ArticleStatus;
import com.example.kunuzdemo.enums.Language;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity(name = "article")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Article extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false, name = "created_by")
    private UserEntity createdBy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Media media;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status = ArticleStatus.CREATED;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    private LocalDateTime publishedDate;

    private Double viewCount = 0D;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    private boolean deleted;
}
