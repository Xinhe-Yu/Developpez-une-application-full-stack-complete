package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
  private Long id;

  @NonNull
  private String content;

  private UserBasicDto user;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
