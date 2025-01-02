package com.openclassrooms.mddapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  @Query("SELECT a FROM Article a WHERE a.topic IN " +
      "(SELECT t FROM User u JOIN u.topics t WHERE u = :user) " +
      "ORDER BY a.createdAt DESC")
  Page<Article> findByTopicIn(@Param("user") User user, Pageable pageable);
}
