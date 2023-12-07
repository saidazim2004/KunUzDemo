package com.example.kunuzdemo.dtos.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {

    private String text;
    private UUID userID;
    private UUID articleID;
}
