package com.example.kunuzdemo.repository;

import com.example.kunuzdemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category , UUID> {


    @Query(value = "from category c where c.id = :categoryID and c.visible = true")
    Optional<Category> findCategoryById(@Param("categoryID") UUID categoryID);



}
