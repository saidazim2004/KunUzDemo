package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.CommentCreateResponseDto;
import com.example.kunuzdemo.dtos.response.CommentResponseDto;
import com.example.kunuzdemo.service.comment.CommentService;
import com.example.kunuzdemo.service.comment.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentServiceImpl commentService ;
    @PostMapping("/create")
    public ResponseEntity<CommentResponseDto> create(@RequestBody CommentCreateResponseDto commentCreateResponseDto){
        return ResponseEntity.ok(commentService.create(commentCreateResponseDto));

    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{commentID}")
    public ResponseEntity<CommentResponseDto> getByID(
            @PathVariable UUID commentID
    ) {
        return ResponseEntity.ok(commentService.getByID(commentID));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update-by-id/{commentID}")
    public  ResponseEntity<CommentResponseDto> updateById(
            @PathVariable UUID commentID,
            @RequestParam String text
    ) {
        return ResponseEntity.ok(commentService.updateById(commentID, text));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/delete/{commentID}")
    public ResponseEntity<String> delete(@PathVariable UUID commentID) {
        return ResponseEntity.ok(commentService.deleteById(commentID));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-selected/{commentIDs}")
    public ResponseEntity<String> deleteSelected(
            @PathVariable List<UUID> commentIDs) {
        return ResponseEntity.ok(commentService.deleteSelected(commentIDs));
    }
}
