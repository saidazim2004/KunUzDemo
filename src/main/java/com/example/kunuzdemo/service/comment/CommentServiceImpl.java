package com.example.kunuzdemo.service.comment;

import com.example.kunuzdemo.dtos.request.CommentCreateResponseDto;
import com.example.kunuzdemo.dtos.response.CommentResponseDto;
import com.example.kunuzdemo.entity.Article;
import com.example.kunuzdemo.entity.Comment;
import com.example.kunuzdemo.entity.UserEntity;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.CommentRepository;
import com.example.kunuzdemo.service.article.ArticleService;
import com.example.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserService userService ;
    private final CommentRepository commentRepository ;
    private final ModelMapper modelMapper ;
    private final ArticleService articleService ;



    @Override
    public CommentResponseDto create(CommentCreateResponseDto commentCreateResponseDto) {
        UserEntity user = userService.getUserByID(commentCreateResponseDto.getUserID());
        Article article = articleService.findById(commentCreateResponseDto.getArticleID());
        Comment comment = Comment.builder()
                .text(commentCreateResponseDto.getText())
                .user(user)
                .article(article)
                .build();
        return modelMapper.map(commentRepository.save(comment), CommentResponseDto.class);
    }



    @Override
    public CommentResponseDto getByID(UUID commentID) {
        return modelMapper.map(getByCommentId(commentID), CommentResponseDto.class);


    }
    private Comment getByCommentId(UUID commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new DataNotFoundException("comment not found with ID :" + commentId));
    }



    @Override
    public CommentResponseDto updateById(UUID commentID, String text) {
        Comment comment = getByCommentId(commentID);
        comment.setText(text);
        return modelMapper.map(commentRepository.save(comment), CommentResponseDto.class);

    }


    @Override
    public String deleteById(UUID commentID) {
        if(!commentRepository.existsById(commentID))
            throw new DataNotFoundException("comment not found with ID :" + commentID);
        commentRepository.deleteById(commentID);
        return "Successfully deleted!";
    }

    @Override
    public String deleteSelected(List<UUID> commentIDs) {
        for (UUID commentID : commentIDs) {
            deleteById(commentID);
        }
        return "Successfully deleted!";
    }


}
