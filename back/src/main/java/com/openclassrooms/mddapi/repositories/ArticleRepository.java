package com.openclassrooms.mddapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  List<Article> findByTopicIn(List<Topic> userTopics);
}
