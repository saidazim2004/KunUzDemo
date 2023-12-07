package com.example.kunuzdemo.repository;

import com.example.kunuzdemo.entity.UserEntity;
import com.example.kunuzdemo.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.net.ContentHandler;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity , UUID> {
    @Query("from users u where not u.deleted and u.id = :userId")
    Optional<UserEntity> getUserByID(@Param("userId") UUID id);
    @Query("from users u where not u.deleted")
    Page<UserEntity> findAllUsers(Pageable pageable);

    @Query("select u from users u where u.role = :userRole")
    Page<UserEntity> filterByRole(@Param("userRole") UserRole userRole, Pageable pageable);

    @Query("from users u where u.deleted = true ")
    Page<UserEntity> findAllDeleted(Pageable pageable);

    @Query("select u from users u where u.email =:email and not u.deleted")
    Optional<UserEntity> findByEmail(String email);

    boolean existsUserByEmail(String email);

    @Query(value = """
           select * from users u where lower(u.email) like 
           lower(concat(:email, '%') and not u.deleted)
           """, nativeQuery = true)
    Page<UserEntity> searchByEmail(String email, Pageable pageable);


    UserEntity getUserEntityById(UUID id);


}
