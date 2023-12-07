package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.LikeCreateDto;
import com.example.kunuzdemo.dtos.response.LikeResponseDto;
import com.example.kunuzdemo.service.like.LikeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeService ;
    @PostMapping("/create")
    public ResponseEntity<LikeResponseDto> createAndUpdate(@RequestBody LikeCreateDto likeCreateDto){
        LikeResponseDto likeResponseDto = likeService.create(likeCreateDto);
        return ResponseEntity.ok(likeResponseDto);
    }

    @DeleteMapping("/{likeID}")
    public ResponseEntity<String> delete(
            @PathVariable UUID likeID
    ) {
        likeService.deleteByID(likeID);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
