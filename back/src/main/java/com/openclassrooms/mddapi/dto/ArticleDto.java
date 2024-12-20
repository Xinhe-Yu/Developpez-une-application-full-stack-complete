package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
  private Long id;

  private String title;

  private String content;

  private UserBasicDto user;

  private TopicDto topic;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
