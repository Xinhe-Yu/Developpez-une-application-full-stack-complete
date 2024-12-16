package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.ArticleService;

@Component
@Mapper(componentModel = "spring", imports = {

})
public abstract CommentMapper implements EntityMapper<CommentDto, Comment> {
  @Autowired
  ArticleService articleService;

  @Mappings({

  })
}
