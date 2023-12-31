package com.example.kunuzdemo.entity;

import com.example.kunuzdemo.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Like extends BaseEntity {
    @Column(nullable = false, name = "like_status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "article_id")
    private Article article;
}
