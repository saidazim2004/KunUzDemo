package com.example.kunuzdemo.repository;

import com.example.kunuzdemo.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like , UUID> {
//    @Query("from likes l where l.status = 'LIKE' and l.article.id =:articleID and l.user.id=:userId")
//    Optional<Like> getByUserIdAndArticleId(@Param("userID") UUID userID,@Param("articleID") UUID articleID);

    @Query("FROM likes l WHERE l.status = 'LIKE' AND l.article.id = :articleId AND l.user.id = :userId")
    Optional<Like> getByUserIdAndArticleId(@Param("userId") UUID userId, @Param("articleId") UUID articleId);




}
