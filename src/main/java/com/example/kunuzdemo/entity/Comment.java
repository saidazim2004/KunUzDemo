package com.example.kunuzdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment extends BaseEntity{
    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
}
