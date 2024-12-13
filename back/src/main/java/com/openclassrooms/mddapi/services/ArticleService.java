package com.openclassrooms.mddapi.services;

import jakarta.transaction.Transactional;

// @Service
@Transactional
public class ArticleService {
  // @Autowired
  // private ArticleRepository articleRepository;

  // @Autowired
  // private UserRepository userRepository;

  // @Autowired
  // private UserService userService;

  // @Autowired
  // private TopicRepository topicRepository;

  // public List<Article> getAllArticles(CustomUserDetails userDetails) {
  // User user = userRepository.findByEmail(userDetails.getEmail());
  // if (user == null) {
  // throw new UsernameNotFoundException("User not found");
  // }

  // List<Topic> userTopics = user.getTopics();
  // List<Article> articles = articleRepository.findByTopicIn(userTopics);
  // return articles;
  // }

  // public void createArticle(Article article, CustomUserDetails userDetails) {
  // User user = userRepository.findByEmail(userDetails.getEmail());
  // if (user == null) {
  // throw new UsernameNotFoundException("User not found");
  // }

  // article.setUser(user);

  // articleRepository.save(article);
  // }
}
