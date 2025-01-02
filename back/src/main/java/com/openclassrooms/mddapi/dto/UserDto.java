package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private Long id;

  private String email;

  private String username;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
