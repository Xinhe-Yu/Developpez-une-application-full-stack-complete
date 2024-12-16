package com.openclassrooms.mddapi.services;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;

@Service
public class CommentService {
  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private UserRepository userRepository;

  public void createComment(Long articleId, Comment comment, CustomUserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Article artice = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));

    comment.setArticle(artice);
    comment.setUser(user);
    commentRepository.save(comment);
  }

  public Page<Comment> getCommentsByArticleId(Long articleId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return commentRepository.findByArticleId(articleId, pageable);
  }
}
