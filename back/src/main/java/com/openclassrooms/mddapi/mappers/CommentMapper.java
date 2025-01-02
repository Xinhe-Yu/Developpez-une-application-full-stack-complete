package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;

@Component
@Mapper(componentModel = "spring", imports = { UserMapper.class })
public abstract class CommentMapper implements EntityMapper<CommentDto, Comment> {
  public abstract CommentDto toDto(Comment comment);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "article", ignore = true)
  public abstract Comment toEntity(CommentDto commentDto);
}
