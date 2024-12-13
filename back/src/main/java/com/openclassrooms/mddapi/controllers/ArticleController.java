package com.openclassrooms.mddapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "auth", description = "Articles API")
public class ArticleController {

  // @Autowired
  // private ArticleService articleService;

  // @Autowired
  // private ArticleMapper articleMapper;

  // @GetMapping
  // public ResponseEntity<ApiResponseDto> getAllArticles(@AuthenticationPrincipal
  // CustomUserDetails userDetails) {
  // if (userDetails == null) {
  // ErrorResponseDto response = new ErrorResponseDto("User not authenticated");
  // return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  // }

  // List<Article> articles = articleService.getAllArticles(userDetails);
  // List<ArticleDto> articleDtos =
  // articles.stream().map(articleMapper::toDto).collect(Collectors.toList());
  // ArticleListDto response = new ArticleListDto(articleDtos);
  // return ResponseEntity.ok(response);
  // }

  // @PostMapping
  // public ResponseEntity<MsgResponseDto> createArticle(@Valid @RequestBody
  // ArticleDto articleDto,
  // @AuthenticationPrincipal CustomUserDetails userDetails) {
  // Article article = articleMapper.toEntity(articleDto);
  // articleService.createArticle(article, userDetails);
  // MsgResponseDto response = new MsgResponseDto("Rental created");
  // return ResponseEntity.ok(response);
  // }
}
