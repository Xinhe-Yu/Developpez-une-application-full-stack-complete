package com.openclassrooms.mddapi.mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.models.Article;

@Component
public class ArticleMapper {

  // @Autowired
  // private UserRepository userRepository;

  // @Autowired
  // private TopicRepository topicRepository;

  public ArticleDto toDto(Article article) {
    if (article == null) {
      return null;
    }

    ArticleDto dto = new ArticleDto();
    dto.setId(article.getId());
    dto.setTitle(article.getTitle());
    dto.setContent(article.getContent());
    dto.setTopicId(article.getTopic().getId());
    dto.setUserId(article.getUser().getId());
    dto.setCreatedAt(article.getCreatedAt());
    dto.setUpdatedAt(article.getUpdatedAt());
    return dto;
  }

  public List<ArticleDto> toDtoList(List<Article> articles) {
    if (articles == null) {
      return Collections.emptyList();
    }
    return articles.stream().map(this::toDto)
        .collect(Collectors.toList());
  }

  public Article toEntity(ArticleDto articleDto) {
    if (articleDto == null) {
      return null;
    }

    Article article = new Article();
    article.setId(articleDto.getId());
    article.setTitle(articleDto.getTitle());
    article.setContent(articleDto.getContent());
    article.setCreatedAt(articleDto.getCreatedAt());
    article.setUpdatedAt(articleDto.getUpdatedAt());

    return article;
  }
}
