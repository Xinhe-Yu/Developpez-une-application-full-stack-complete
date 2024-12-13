package com.openclassrooms.mddapi.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;

@Component
public class UserMapper {

  public UserDto convertToDTO(User user) {
    if (user == null) {
      return null;
    }

    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setEmail(user.getEmail());
    dto.setCreatedAt(user.getCreatedAt());
    dto.setUpdatedAt(user.getUpdatedAt());

    if (user.getTopics() != null) {
      dto.setTopics(user.getTopics().stream()
          .map(Topic::getId)
          .collect(Collectors.toList()));
    }
    return dto;
  }
}
