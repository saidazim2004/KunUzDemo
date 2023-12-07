package com.example.kunuzdemo.service.like;

import com.example.kunuzdemo.dtos.request.LikeCreateDto;
import com.example.kunuzdemo.dtos.response.LikeResponseDto;
import com.example.kunuzdemo.entity.Article;
import com.example.kunuzdemo.entity.Like;
import com.example.kunuzdemo.entity.UserEntity;
import com.example.kunuzdemo.enums.LikeStatus;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.LikeRepository;

import com.example.kunuzdemo.service.article.ArticleServiceImpl;
import com.example.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository ;
    private final ModelMapper modelMapper ;
    private final ArticleServiceImpl articleService ;
    private final UserService userService ;


    @Override
    public LikeResponseDto create(LikeCreateDto likeCreateDto) {
        Optional<Like> like = likeRepository.getByUserIdAndArticleId(likeCreateDto.getUserID() , likeCreateDto.getArticleID());
        if (like.isPresent()){
            like.get().setStatus(LikeStatus.valueOf(likeCreateDto.getStatus()));
            likeRepository.save(like.get());
            return modelMapper.map(like.get(),LikeResponseDto.class);
        }
        UserEntity user = userService.getUserByID(likeCreateDto.getUserID());
        Article article = articleService.findById(likeCreateDto.getArticleID());
        Like likeSave = Like.builder()
                .status(LikeStatus.valueOf(likeCreateDto.getStatus()))
                .user(user)
                .article(article)
                .build();
        Like map = modelMapper.map(likeSave, Like.class);
        likeRepository.save(map);
        return modelMapper.map(map,LikeResponseDto.class);
    }



    @Override
    public void deleteByID(UUID likeID) {
        if (!likeRepository.existsById(likeID))
            throw new DataNotFoundException("Like not found with ID: " + likeID);
        likeRepository.deleteById(likeID);
    }
}
