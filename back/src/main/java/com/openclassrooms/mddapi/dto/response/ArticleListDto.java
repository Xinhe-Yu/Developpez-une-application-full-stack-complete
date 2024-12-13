package com.openclassrooms.mddapi.dto.response;

import java.util.List;

import com.openclassrooms.mddapi.dto.ArticleDto;

public class ArticleListDto implements ApiResponseDto {
  private List<ArticleDto> articles;

  public ArticleListDto(List<ArticleDto> articles) {
    this.articles = articles;
  }

  public List<ArticleDto> getArticles() {
    return articles;
  }

  public void setError(List<ArticleDto> articles) {
    this.articles = articles;
  }
}
