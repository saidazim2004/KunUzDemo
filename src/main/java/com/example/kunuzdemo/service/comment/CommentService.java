package com.example.kunuzdemo.service.comment;

import com.example.kunuzdemo.dtos.request.CommentCreateResponseDto;
import com.example.kunuzdemo.dtos.response.CommentResponseDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponseDto create(CommentCreateResponseDto commentCreateResponseDto);


    CommentResponseDto getByID(UUID commentID);

    CommentResponseDto updateById(UUID commentID, String text);

    String deleteById(UUID commentID);

    String deleteSelected(List<UUID> commentIDs);

}
