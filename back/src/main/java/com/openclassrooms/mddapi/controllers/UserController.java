package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CustomUserDetails;
import com.openclassrooms.mddapi.services.CustomUserDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "user", description = "User API")
public class UserController {
  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private TopicMapper topicMapper;

  @Operation(summary = "Get current user information", description = "Retrieve information about the currently authenticated user")
  @GetMapping("/profile")
  public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetailsService.getCurrentUser(userDetails.getEmail());

    UserDto userDto = userMapper.toDto(user);
    return ResponseEntity.ok(userDto);
  }

  @GetMapping("/subs")
  public ResponseEntity<List<TopicDto>> getUserSubscriptions(@AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetailsService.getCurrentUser(userDetails.getEmail());

    List<Topic> topics = user.getTopics();
    List<TopicDto> topicDtos = topicMapper.toDto(topics);
    return ResponseEntity.ok(topicDtos);
  }
}
