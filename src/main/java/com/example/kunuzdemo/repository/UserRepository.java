package com.example.kunuzdemo.repository;

import com.example.kunuzdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity , UUID> {
    @Query("select u from users u where  u.email=:email and not u.deleted")
    Optional<UserEntity> findByEmail(String email);

    boolean existsUserByEmail(String email);

}
