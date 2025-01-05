package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;

@Service
public class CommentService {
  @Autowired
  private CommentRepository commentRepository;

  public void createComment(Comment comment) {
    commentRepository.save(comment);
  }

  public Page<Comment> getCommentsByArticleId(Long articleId, Pageable pageable) {
    return commentRepository.findByArticleId(articleId, pageable);
  }
}
