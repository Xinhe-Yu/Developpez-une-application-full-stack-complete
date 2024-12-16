package com.openclassrooms.mddapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PaginationDto;
import com.openclassrooms.mddapi.dto.response.ApiResponseDto;
import com.openclassrooms.mddapi.dto.response.ArticleListDto;
import com.openclassrooms.mddapi.dto.response.CommentsResponseDto;
import com.openclassrooms.mddapi.dto.response.ErrorResponseDto;
import com.openclassrooms.mddapi.dto.response.MsgResponseDto;
import com.openclassrooms.mddapi.mappers.ArticleMapper;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.CustomUserDetails;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "auth", description = "Articles API")
public class ArticleController {
  @Autowired
  private ArticleService articleService;

  @Autowired
  private ArticleMapper articleMapper;

  @Autowired
  private CommentService commentService;

  @Autowired
  private CommentMapper commentMapper;

  @GetMapping
  public ResponseEntity<ApiResponseDto> getAllArticles(@AuthenticationPrincipal CustomUserDetails userDetails) {
    if (userDetails == null) {
      ErrorResponseDto response = new ErrorResponseDto("User not authenticated");
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    List<Article> articles = articleService.getAllArticles(userDetails);
    List<ArticleDto> articleDtos = articles.stream().map(articleMapper::toDto).collect(Collectors.toList());
    ArticleListDto response = new ArticleListDto(articleDtos);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
    Article article = articleService.getArticleById(id);
    ArticleDto articleDto = articleMapper.toDto(article);
    return ResponseEntity.ok(articleDto);
  }

  @PostMapping
  public ResponseEntity<MsgResponseDto> createArticle(@Valid @RequestBody ArticleDto articleDto,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    Article article = articleMapper.toEntity(articleDto);
    articleService.createArticle(article, userDetails);
    MsgResponseDto response = new MsgResponseDto("Article created");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{id}/comments")
  public ResponseEntity<CommentsResponseDto> getComments(
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "25") Integer limit) {
    Page<Comment> commentPage = commentService.getCommentsByArticleId(id, page, limit);
    List<CommentDto> commentDtos = commentPage.stream().map(commentMapper::toDto).collect(Collectors.toList());

    PaginationDto paginationDto = new PaginationDto(
        commentPage.getNumber(),
        commentPage.getTotalPages(),
        commentPage.getSize(),
        commentPage.getTotalElements());

    CommentsResponseDto response = new CommentsResponseDto(commentDtos, paginationDto);

    return ResponseEntity.ok(response);
  }
}
