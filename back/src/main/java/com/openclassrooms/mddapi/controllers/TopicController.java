package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.response.ApiResponseDto;
import com.openclassrooms.mddapi.dto.response.ErrorResponseDto;
import com.openclassrooms.mddapi.dto.response.MsgResponseDto;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CustomUserDetails;
import com.openclassrooms.mddapi.services.CustomUserDetailsService;
import com.openclassrooms.mddapi.services.SubscriptionService;
import com.openclassrooms.mddapi.services.TopicService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/topics")
@Tag(name = "topics", description = "Topics API")
public class TopicController {
  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private TopicService topicService;

  @Autowired
  private TopicMapper topicMapper;

  @Autowired
  private SubscriptionService subscriptionService;

  @GetMapping
  public ResponseEntity<List<TopicDto>> findAll() {
    List<Topic> topics = topicService.findAll();
    List<TopicDto> topicDtos = topicMapper.toDto(topics);
    return ResponseEntity.ok(topicDtos);
  }

  @GetMapping("/subs")
  public ResponseEntity<List<TopicDto>> getUserSubscriptions(@AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetailsService.getCurrentUser(userDetails.getEmail());

    List<Topic> topics = user.getTopics();
    List<TopicDto> topicDtos = topicMapper.toDto(topics);
    return ResponseEntity.ok(topicDtos);
  }

  @PostMapping("/{id}/subscribe")
  public ResponseEntity<ApiResponseDto> subscribe(@PathVariable Long id,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    try {
      User user = userDetailsService.getCurrentUser(userDetails.getEmail());
      subscriptionService.subscribe(id, user);
      MsgResponseDto response = new MsgResponseDto("Abonnement réussi");
      return ResponseEntity.ok(response);
    } catch (ResponseStatusException e) {
      ErrorResponseDto response = new ErrorResponseDto("Échec abonnement : " +
          e.getReason());
      return new ResponseEntity<>(response, e.getStatusCode());
    } catch (Exception e) {
      ErrorResponseDto response = new ErrorResponseDto("Échec abonnement : " +
          e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}/subscribe")
  public ResponseEntity<ApiResponseDto> unsubscribe(@PathVariable Long id,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    try {
      User user = userDetailsService.getCurrentUser(userDetails.getEmail());
      subscriptionService.unsubscribe(id, user);
      MsgResponseDto response = new MsgResponseDto("Désabonnement réussi");
      return ResponseEntity.ok(response);
    } catch (ResponseStatusException e) {
      ErrorResponseDto response = new ErrorResponseDto("Échec désabonnement : " +
          e.getReason());
      return new ResponseEntity<>(response, e.getStatusCode());
    } catch (Exception e) {
      ErrorResponseDto response = new ErrorResponseDto("Échec désabonnement : " +
          e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }
}
