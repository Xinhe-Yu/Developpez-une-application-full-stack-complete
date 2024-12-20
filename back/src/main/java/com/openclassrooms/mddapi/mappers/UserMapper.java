package com.openclassrooms.mddapi.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserBasicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper implements EntityMapper<UserDto, User> {
  public abstract UserDto toDto(User user);

  public abstract UserBasicDto toBasicDto(User user);

  @Mapping(target = "password", ignore = true)
  @Mapping(target = "topics", ignore = true)
  public abstract User toEntity(UserDto userDto);

  public abstract List<UserBasicDto> toBasicDtoList(List<User> users);
}
