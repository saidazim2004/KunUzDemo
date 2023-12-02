package com.example.kunuzdemo.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCreateDto {
    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "UserID must not be null")
    private UUID userID;

    private Long mediaID;

    @NotNull(message = "RegionID must not be null")
    private UUID regionID;

    @NotNull(message = "CategoryID must not be null")
    private UUID categoryID;

    @NotBlank(message = "Language must not be blank")
    private String language;
}
