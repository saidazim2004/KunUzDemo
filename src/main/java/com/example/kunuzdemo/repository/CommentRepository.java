package com.example.kunuzdemo.repository;

import com.example.kunuzdemo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment , UUID> {

}
