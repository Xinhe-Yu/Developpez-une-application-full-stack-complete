package com.openclassrooms.mddapi.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TopicRepository topicRepository;

  @Transactional
  public void subscribe(Long topicId, User user) {
    Topic topic = topicRepository.findById(topicId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thème non trouvé"));

    if (user.getTopics() == null) {
      user.setTopics(new ArrayList<>());
    }

    if (user.getTopics().stream().anyMatch(t -> t.getId().equals(topicId))) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Déjà abonné à ce thème");
    }
    user.getTopics().add(topic);
    userRepository.save(user);
  }

  @Transactional
  public void unsubscribe(Long topicId, User user) {
    if (user.getTopics() == null ||
        !user.getTopics().removeIf(t -> t.getId().equals(topicId))) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Non abonné à ce thème");
    }

    userRepository.save(user);
  }
}
