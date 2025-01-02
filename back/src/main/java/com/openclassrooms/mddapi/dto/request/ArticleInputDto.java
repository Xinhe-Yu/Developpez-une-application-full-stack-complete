package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInputDto {
  private Long id;

  @NotBlank
  @Size(max = 255)
  private String title;

  @NotBlank
  private String content;

  @NotNull
  private Long topicId;
}
