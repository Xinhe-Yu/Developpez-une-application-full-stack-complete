package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArticleService {
  @Autowired
  private ArticleRepository articleRepository;

  public Page<Article> getAllArticles(User user, Pageable pageable) {
    return articleRepository.findByTopicIn(user, pageable);
  }

  public Article getArticleById(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Article not found"));

    return article;
  }

  public Article createArticle(Article article) {
    return articleRepository.save(article);
  }
}
