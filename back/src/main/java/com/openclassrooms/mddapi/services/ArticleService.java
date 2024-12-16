package com.openclassrooms.mddapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
// import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArticleService {
  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private UserRepository userRepository;

  // @Autowired
  // private UserService userService;

  // @Autowired
  // private TopicRepository topicRepository;

  public List<Article> getAllArticles(CustomUserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    List<Topic> userTopics = user.getTopics();
    List<Article> articles = articleRepository.findByTopicIn(userTopics);
    return articles;
  }

  public Article getArticleById(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("Article not found"));

    return article;
  }

  public void createArticle(Article article, CustomUserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    article.setUser(user);
    articleRepository.save(article);
  }
}
