package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.ArticleCreateDto;
import com.example.kunuzdemo.dtos.request.ArticleUpdateDTO;

import com.example.kunuzdemo.dtos.response.ArticleResponseDto;
import com.example.kunuzdemo.service.article.ArticleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService ;


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'PUBLISHER')")
    @PostMapping("/create-article")
    public ResponseEntity<ArticleResponseDto> create(
            @Valid @RequestBody ArticleCreateDto createDTO
    ) {
        ArticleResponseDto articleResponseDTO = articleService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDTO);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ArticleResponseDto> getById(@RequestParam UUID articleId){
        ArticleResponseDto articleResponseDto = articleService.getById(articleId);
        return ResponseEntity.ok(articleResponseDto);
    }

    @GetMapping("/get-by-language")
    public ResponseEntity<List<ArticleResponseDto>> geArticlesByLanguage(@RequestParam String language ,
                                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                         @RequestParam(required = false, defaultValue = "10") Integer size){
        List<ArticleResponseDto> articleResponseDtos = articleService.getByLanguage(language , page , size);

        return ResponseEntity.ok(articleResponseDtos);
    }

    @GetMapping("/get-recommended-articles")
    public ResponseEntity<List<ArticleResponseDto>> findRecommendedArticles(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                            @RequestParam(required = false, defaultValue = "10") Integer size){
        List<ArticleResponseDto> articleResponseDtos = articleService.findRecommendedArticles(page,size);
        return ResponseEntity.ok(articleResponseDtos);
    }



    @GetMapping("/get-by-title")
    public ResponseEntity<List<ArticleResponseDto>> getByTitle(@RequestParam String titleName,
                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) {

        List<ArticleResponseDto> articleResponseDtos = articleService.getByTitle(titleName , page , size);
        return ResponseEntity.ok(articleResponseDtos);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'PUBLISHER')")
    @PostMapping("/change-article-status-published")
    public ResponseEntity<ArticleResponseDto> changeArticleStatus(@RequestParam UUID articleId){
        ArticleResponseDto articleResponseDto = articleService.changeArticleStatus(articleId);
        return ResponseEntity.ok(articleResponseDto);
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/get-by-publisher")
    public ResponseEntity<List<ArticleResponseDto>> getByPublisher( @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        List<ArticleResponseDto> articleResponseDtos = articleService.getByPublisher(page , size);
        return ResponseEntity.ok(articleResponseDtos);
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/get-all-blocked")
    public ResponseEntity<List<ArticleResponseDto>> getAllUnVisible(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(articleService.getAllBlocked(page, size));
    }



    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/get-by-region")
    public ResponseEntity<List<ArticleResponseDto>> getByRegion(@RequestParam UUID regionID,
                                                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                @RequestParam(required = false, defaultValue = "10") Integer size){

        System.out.println("REGION ID "+regionID);
        List<ArticleResponseDto> byRegionId = articleService.getByRegionId(regionID, page, size);
        return ResponseEntity.ok(byRegionId);
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/get-latest-news")
    public ResponseEntity<List<ArticleResponseDto>> getLatestNews(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        return ResponseEntity.ok(articleService.getLatestNews(page, size));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("/{articleID}")
    public ResponseEntity<ArticleResponseDto> updateById(
            @PathVariable UUID articleID,
            @RequestBody @Valid ArticleUpdateDTO updateDTO
    ) {
        return ResponseEntity.ok(articleService.updateById(articleID, updateDTO));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("/{articleID}")
    public ResponseEntity<String> deleteById(
            @PathVariable UUID articleID
    ) {
        return ResponseEntity.ok(articleService.deleteById(articleID));
    }





    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("/delete-selected")
    public ResponseEntity<String> deleteSelected(
            @RequestParam List<UUID> articleIDs
    ) {
        return ResponseEntity.ok(articleService.deleteSelected(articleIDs));
    }

}
