package com.example.kunuzdemo.repository;


import com.example.kunuzdemo.entity.Article;
import com.example.kunuzdemo.enums.Language;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.net.ContentHandler;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article , UUID> {

    Optional<Article> getArticleById(UUID id);

    @Query("from article a where a.language =:language and a.status = 'PUBLISHED' and not a.deleted")
    Page<Article> findByLanguage(Language language, Pageable pageable);

    @Query("from article a where a.status = 'PUBLISHED' and not a.deleted order by a.viewCount DESC")
    Page<Article> findRecommendedArticles(Pageable pageable);
    @Query(value = """
           select * from article a where lower(a.title) like 
           lower(concat('%', :title, '%')) and a.status = 'PUBLISHED' and not a.deleted
           """, nativeQuery = true)
    Page<Article> getByTitle(@Param("title") String title, Pageable pageable);


    @Query(value = "from article a where a.status = 'PUBLISHED' and not a.deleted")
    Page<Article> findByPublished(Pageable pageable);


    @Query(value = "from article a where a.status = 'BLOCKED'")
    Page<Article> findArticleByStatusBlocked(Pageable pageable);




    @Query(value = "from article a where not a.deleted and a.region.id =:regionID and a.region.visible = true ")
    Page<Article> findArticleByRegion(@Param("regionID")UUID regionID, Pageable pageable);

    @Query(value = "from article a where not a.deleted")
    Page<Article> findLatestNews(Pageable pageable);

}
