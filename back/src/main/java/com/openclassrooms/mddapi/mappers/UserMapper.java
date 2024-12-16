package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.TopicService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", imports = {
    Arrays.class,
    Collections.class,
    Optional.class,
    Collectors.class,
    Topic.class
})
public abstract class UserMapper implements EntityMapper<UserDto, User> {
  @Autowired
  TopicService topicService;

  @Mappings({
      @Mapping(target = "topics", expression = "java(Optional.ofNullable(userDto.getTopics()).orElseGet(Collections::emptyList).stream().map(topic_id -> { Topic topic = this.topicService.findById(topic_id); if (topic != null) { return topic; } return null;}).collect(Collectors.toList()))")
  })
  public abstract User toEntity(UserDto userDto);

  @Mappings({
      @Mapping(target = "topics", expression = "java(Optional.ofNullable(user.getTopics()).orElseGet(Collections::emptyList).stream().map(topic -> topic.getId()).collect(Collectors.toList()))")
  })
  public abstract UserDto toDto(User user);
}
