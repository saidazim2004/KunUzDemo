package com.example.kunuzdemo.dtos.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResponseDto {
    private String status;
    private UUID userID;
    private UUID articleID;
}
