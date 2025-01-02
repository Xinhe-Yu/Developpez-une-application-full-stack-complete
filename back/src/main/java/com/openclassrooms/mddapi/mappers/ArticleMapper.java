package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.request.ArticleInputDto;
import com.openclassrooms.mddapi.models.Article;

@Component
@Mapper(componentModel = "spring", uses = { UserMapper.class, TopicMapper.class })
public abstract class ArticleMapper implements EntityMapper<ArticleDto, Article> {
  public abstract ArticleDto toDto(Article article);

  @Override
  @Mapping(target = "topic", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  public abstract Article toEntity(ArticleDto articleDto);

  @Mapping(target = "topic", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  public abstract Article toEntity(ArticleInputDto articleDto);
}
