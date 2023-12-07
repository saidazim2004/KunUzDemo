package com.example.kunuzdemo.service.like;

import com.example.kunuzdemo.dtos.request.LikeCreateDto;
import com.example.kunuzdemo.dtos.response.LikeResponseDto;

import java.util.UUID;

public interface LikeService {
    LikeResponseDto create(LikeCreateDto likeCreateDto);

    void deleteByID(UUID likeID);

}
