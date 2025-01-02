package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterDto {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
