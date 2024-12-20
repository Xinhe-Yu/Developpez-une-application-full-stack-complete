package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.request.UpdateDto;
import com.openclassrooms.mddapi.dto.response.ApiResponseDto;
import com.openclassrooms.mddapi.dto.response.ErrorResponseDto;
import com.openclassrooms.mddapi.dto.response.MsgResponseDto;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CustomUserDetailsService;
import com.openclassrooms.mddapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

  @Autowired
  private UserService userService;

  @Operation(summary = "Get current user information", description = "Retrieve information about the currently authenticated user")
  @GetMapping("/profile")
  public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    User user = userDetailsService.getCurrentUser(userDetails.getUsername());

    UserDto userDto = userMapper.toDto(user);
    return ResponseEntity.ok(userDto);
  }

  @GetMapping("/subs")
  public ResponseEntity<List<TopicDto>> getUserSubscriptions(@AuthenticationPrincipal UserDetails userDetails) {
    User user = userDetailsService.getCurrentUser(userDetails.getUsername());

    List<Topic> topics = user.getTopics();
    List<TopicDto> topicDtos = topicMapper.toDto(topics);
    return ResponseEntity.ok(topicDtos);
  }

  @Operation(summary = "Update current user information", description = "Updateinformation about the currently authenticated user")
  @PutMapping("/profile")
  public ResponseEntity<ApiResponseDto> updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails,
      @RequestBody UpdateDto userDto) {
    try {
      userService.validateAndUpdateUser(userDto, userDetails);
      MsgResponseDto response = new MsgResponseDto("User updated successfully");
      return ResponseEntity.ok(response);
    } catch (ResponseStatusException e) {
      ErrorResponseDto response = new ErrorResponseDto("Update failed: " +
          e.getReason());
      return new ResponseEntity<>(response, e.getStatusCode());
    } catch (Exception e) {
      ErrorResponseDto response = new ErrorResponseDto("Update failed: " +
          e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }
}
