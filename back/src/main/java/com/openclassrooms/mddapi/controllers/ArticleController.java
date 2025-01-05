package com.openclassrooms.mddapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PaginationDto;
import com.openclassrooms.mddapi.dto.request.ArticleInputDto;
import com.openclassrooms.mddapi.dto.request.CommentInputDto;
import com.openclassrooms.mddapi.dto.response.ArticlesResponseDto;
import com.openclassrooms.mddapi.dto.response.CommentsResponseDto;
import com.openclassrooms.mddapi.dto.response.MsgResponseDto;
import com.openclassrooms.mddapi.mappers.ArticleMapper;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.mappers.PaginationMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.CustomUserDetails;
import com.openclassrooms.mddapi.services.CustomUserDetailsService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "articles", description = "Articles API")
public class ArticleController {
  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private ArticleService articleService;

  @Autowired
  private ArticleMapper articleMapper;

  @Autowired
  private CommentService commentService;

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private PaginationMapper paginationMapper;

  @GetMapping
  public ResponseEntity<ArticlesResponseDto> getAllArticles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "25") Integer size,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetailsService.getCurrentUser(userDetails.getEmail());

    Page<Article> articlePage = articleService.getAllArticles(user, PageRequest.of(page, size));
    List<ArticleDto> articleDtos = articlePage.stream().map(articleMapper::toDto).collect(Collectors.toList());
    PaginationDto paginationDto = paginationMapper.toDto(articlePage);

    ArticlesResponseDto response = new ArticlesResponseDto(articleDtos, paginationDto);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
    Article article = articleService.getArticleById(id);
    ArticleDto articleDto = articleMapper.toDto(article);
    return ResponseEntity.ok(articleDto);
  }

  @PostMapping
  public ResponseEntity<MsgResponseDto> createArticle(@Valid @RequestBody ArticleInputDto articleDto,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    Article article = articleMapper.toEntity(articleDto, userDetails);
    articleService.createArticle(article);
    MsgResponseDto response = new MsgResponseDto("Article created");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<CommentsResponseDto> getComments(
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "25") Integer size) {
    Page<Comment> commentPage = commentService.getCommentsByArticleId(id, PageRequest.of(page, size));
    List<CommentDto> commentDtos = commentPage.stream().map(commentMapper::toDto).collect(Collectors.toList());

    PaginationDto paginationDto = paginationMapper.toDto(commentPage);

    CommentsResponseDto response = new CommentsResponseDto(commentDtos, paginationDto);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/{id}/comments")
  public ResponseEntity<MsgResponseDto> createComment(
      @PathVariable Long id,
      @Valid @RequestBody CommentInputDto commentDto,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    Comment comment = commentMapper.toEntity(commentDto, id, userDetails);
    commentService.createComment(comment);
    MsgResponseDto response = new MsgResponseDto("Comment created");
    return ResponseEntity.ok(response);
  }

}
