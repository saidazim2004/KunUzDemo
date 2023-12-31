package com.example.kunuzdemo.service.article;

import com.example.kunuzdemo.dtos.request.ArticleCreateDto;
import com.example.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.example.kunuzdemo.dtos.response.ArticleResponseDto;
import com.example.kunuzdemo.dtos.response.RegionResponseDTO;
import com.example.kunuzdemo.entity.*;
import com.example.kunuzdemo.enums.ArticleStatus;
import com.example.kunuzdemo.enums.Language;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.ArticleRepository;
import com.example.kunuzdemo.service.category.CategoryService;
import com.example.kunuzdemo.service.media.MediaServiceImpl;
import com.example.kunuzdemo.service.region.RegionService;
import com.example.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final UserService userService ;
    private final ModelMapper modelMapper ;
    private final RegionService regionService ;
    private final CategoryService categoryService ;
    private final MediaServiceImpl mediaService ;
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



    @Override
    public ArticleResponseDto getById(UUID articleId) {
        Optional<Article> articleById = articleRepository.getArticleById(articleId);

        if (articleById.isEmpty()){
            throw new DataNotFoundException("Article not found !");
        }
        else {
            articleById.get().setViewCount(articleById.get().getViewCount()+1);
            articleRepository.save(articleById.get());

            return modelMapper.map(articleById.get(), ArticleResponseDto.class);
        }
    }



    @Override
    public List<ArticleResponseDto> getByLanguage(String language, Integer page, Integer size) {
        List<Article> articles =
                articleRepository.findByLanguage(Language.valueOf(language), PageRequest.of(page,size))
                        .getContent();
        return modelMapper.map(articles , new TypeToken<List<ArticleResponseDto>>(){}.getType());
    }



    @Override
    public List<ArticleResponseDto> findRecommendedArticles(Integer page, Integer size) {

        List<Article> articles = articleRepository.findRecommendedArticles(PageRequest.of(page,size)).getContent();
        return modelMapper.map(articles , new TypeToken<List<ArticleResponseDto>>(){}.getType());

    }


    @Override
    public List<ArticleResponseDto> getByTitle(String titleName, Integer page, Integer size) {

        List<Article>articles = articleRepository.getByTitle(titleName ,  PageRequest.of(page,size)).getContent();
        return modelMapper.map(articles , new TypeToken<List<ArticleResponseDto>>(){}.getType()) ;
    }


    @Override
    public ArticleResponseDto changeArticleStatus(UUID articleId) {

        Optional<Article> articleById = articleRepository.getArticleById(articleId);
        articleById.get().setStatus(ArticleStatus.PUBLISHED);

        if (articleById.get().getStatus().equals(ArticleStatus.PUBLISHED)){
            articleById.get().setPublishedDate(LocalDateTime.now());
        }
        articleRepository.save(articleById.get());

        return modelMapper.map(articleById.get() , ArticleResponseDto.class);
    }


    @Override
    public List<ArticleResponseDto> getByPublisher(Integer page, Integer size) {
        return modelMapper.map(articleRepository.findByPublished(PageRequest.of(page,size)).getContent(), new TypeToken<List<ArticleResponseDto>>(){}.getType());

    }



    @Override
    public List<ArticleResponseDto> getAllBlocked(Integer page, Integer size) {
        return modelMapper.map(articleRepository.findArticleByStatusBlocked(PageRequest.of(page, size)).getContent(),
                new TypeToken<List<ArticleResponseDto>>() {}.getType());
    }


    @Override
    public List<ArticleResponseDto> getByRegionId(UUID regionID, Integer page, Integer size) {
        return  modelMapper.map(articleRepository.findArticleByRegion(regionID , PageRequest.of(page,size)).getContent() , new TypeToken<List<ArticleResponseDto>>(){}.getType());
    }


    @Override
    public List<ArticleResponseDto> getLatestNews(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        return modelMapper.map(articleRepository.findLatestNews(PageRequest.of(page, size, sort)).getContent(),
                new TypeToken<List<ArticleResponseDto>>() {}.getType());
    }



    @Override
    public ArticleResponseDto updateById(UUID articleID, ArticleUpdateDTO updateDTO) {
        Optional<Article> articleById = articleRepository.getArticleById(articleID);
        Article article = articleById.get();
        Media media = mediaService.getMediaById(updateDTO.getMediaID());
        System.out.println();
        if(!media.isDeleted()) {
            try {
                article.setLanguage(Language.valueOf(updateDTO.getLanguage()));
                article.setTitle(updateDTO.getTitle());
                article.setDescription(updateDTO.getDescription());
                article.setCategory(categoryService.getCategory(updateDTO.getCategoryID()));
                article.setRegion(regionService.getRegion(updateDTO.getReginID()));
                article.setMedia(media);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Enum type not valid: " + updateDTO.getLanguage());
            }
        }
        return modelMapper.map(articleRepository.save(article), ArticleResponseDto.class);
    }


    @Override
    public String deleteById(UUID articleID) {
        Optional<Article> articleById = articleRepository.getArticleById(articleID);
        Article article = articleById.get();
        article.setDeleted(true);
        articleRepository.save(article);
        return "Successfully deleted!";
    }



    @Override
    public String deleteSelected(List<UUID> articleIDs) {
        for (UUID articleID : articleIDs) {
            deleteById(articleID);
        }
        return "Successfully deleted!";
    }

    @Override
    public Article findById(UUID id) {
        return articleRepository.findById(id).get();
    }
}
