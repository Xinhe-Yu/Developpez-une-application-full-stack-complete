package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repositories.TopicRepository;

public class TopicService {
  @Autowired
  private TopicRepository topicRepository;

  public Topic findById(Long id) {
    return topicRepository.findById(id).orElse(null);
  }
}
