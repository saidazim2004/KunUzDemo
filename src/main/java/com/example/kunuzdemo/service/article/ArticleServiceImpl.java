package com.example.kunuzdemo.service.article;

import com.example.kunuzdemo.dtos.request.ArticleCreateDto;
import com.example.kunuzdemo.dtos.response.ArticleResponseDto;
import com.example.kunuzdemo.dtos.response.RegionResponseDTO;
import com.example.kunuzdemo.entity.*;
import com.example.kunuzdemo.repository.ArticleRepository;
import com.example.kunuzdemo.service.category.CategoryService;
import com.example.kunuzdemo.service.media.MediaService;
import com.example.kunuzdemo.service.region.RegionService;
import com.example.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final UserService userService ;
    private final ModelMapper modelMapper ;
    private final RegionService regionService ;
    private final CategoryService categoryService ;
    private final MediaService mediaService ;
    private final ArticleRepository articleRepository ;

    @Override
    public ArticleResponseDto create(ArticleCreateDto articleCreateDto) {
        UserEntity user = userService.getUserByID(articleCreateDto.getUserID());
        Region region = regionService.getRegion(articleCreateDto.getRegionID());
        Category category = categoryService.getCategory(articleCreateDto.getCategoryID());
        Article article = modelMapper.map(articleCreateDto, Article.class);
        article.setCreatedBy(user);
        article.setRegion(region);
        article.setCategory(category);
        if (articleCreateDto.getMediaID() != null) {
            article.setMedia(mediaService.getMediaById(articleCreateDto.getMediaID()));
        }
        Article savedArticle = articleRepository.save(article);

        RegionResponseDTO regionResponseDTO = modelMapper.map(savedArticle.getRegion(), RegionResponseDTO.class);

        ArticleResponseDto articleResponseDto = modelMapper.map(savedArticle, ArticleResponseDto.class);
        articleResponseDto.setRegion(regionResponseDTO);
        return articleResponseDto ;
    }
}
