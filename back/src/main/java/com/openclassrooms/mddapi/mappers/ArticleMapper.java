package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.request.ArticleInputDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.CustomUserDetails;

@Component
@Mapper(componentModel = "spring", uses = { UserMapper.class, TopicMapper.class })
public abstract class ArticleMapper implements EntityMapper<ArticleDto, Article> {
  @Autowired
  private TopicRepository topicRepository;

  @Autowired
  private UserRepository userRepository;

  public abstract ArticleDto toDto(Article article);

  @Override
  @Mapping(target = "topic", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  public abstract Article toEntity(ArticleDto articleDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "topic", expression = "java(mapTopic(articleDto.getTopicId()))")
  @Mapping(target = "user", expression = "java(mapUser(userDetails))")
  @Mapping(target = "comments", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  public abstract Article toEntity(ArticleInputDto articleDto, CustomUserDetails userDetails);

  protected Topic mapTopic(Long topicId) {
    return topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
  }

  protected User mapUser(CustomUserDetails userDetails) {
    return userRepository.findByEmail(userDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
