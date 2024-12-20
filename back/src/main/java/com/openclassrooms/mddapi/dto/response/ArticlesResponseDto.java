package com.openclassrooms.mddapi.dto.response;

import java.util.List;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.PaginationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlesResponseDto implements ApiResponseDto {
  private List<ArticleDto> data;
  private PaginationDto pagination;
}
