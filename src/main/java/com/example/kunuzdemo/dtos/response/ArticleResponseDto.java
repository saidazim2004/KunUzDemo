package com.example.kunuzdemo.dtos.response;

import com.example.kunuzdemo.entity.*;
import com.example.kunuzdemo.enums.ArticleStatus;
import com.example.kunuzdemo.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArticleResponseDto {


    private String title;
    private String description;
    private Long mediaID;
    private RegionResponseDTO region;
    private CategoryResponseDTO category;
    private String language;
    private LocalDateTime publishedDate;
    private Double viewCount;
    private Double likeCount;
    private Double commentCount;



//    private String title;
//    private String description;
//    private UserEntity createdBy;
//    private Media media;
//
//    private Region region;
//
//    private Category category;
//
//    private ArticleStatus status = ArticleStatus.CREATED;
//
//    private Language language;
//
//    private LocalDateTime publishedDate;
//
//    private Double viewCount = 0D;
//
//
//    private List<Comment> comments;
//
//
//    private List<Like> likes;
//
//    private boolean deleted;
}
