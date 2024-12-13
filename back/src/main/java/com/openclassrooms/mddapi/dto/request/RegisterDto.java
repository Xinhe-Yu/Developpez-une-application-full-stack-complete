package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterDto {
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 50)
  private String username;

  @NotBlank
  @Size(min = 8, max = 255)
  private String password;
}
