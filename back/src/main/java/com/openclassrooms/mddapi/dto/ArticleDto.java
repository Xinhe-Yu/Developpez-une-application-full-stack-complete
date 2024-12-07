package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
  private Long id;

  @NotNull
  @Size(max = 255)
  private String title;

  @NotNull
  private String content;

  @NotNull
  private Long user_id;

  @NotNull
  private Long topic_id;

  private List<Long> comments;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
