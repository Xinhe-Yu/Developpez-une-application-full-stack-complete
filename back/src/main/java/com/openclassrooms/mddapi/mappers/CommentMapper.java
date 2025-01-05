package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.request.CommentInputDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.CustomUserDetails;

@Component
@Mapper(componentModel = "spring", imports = { UserMapper.class, ArticleMapper.class })
public abstract class CommentMapper implements EntityMapper<CommentDto, Comment> {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ArticleRepository articleRepository;

  public abstract CommentDto toDto(Comment comment);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "article", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  public abstract Comment toEntity(CommentDto commentDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", expression = "java(mapUser(userDetails))")
  @Mapping(target = "article", expression = "java(mapArticle(articleId))")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  public abstract Comment toEntity(CommentInputDto commentDto, Long articleId, CustomUserDetails userDetails);

  protected Article mapArticle(Long articleId) {
    return articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
  }

  protected User mapUser(CustomUserDetails userDetails) {
    return userRepository.findByEmail(userDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
