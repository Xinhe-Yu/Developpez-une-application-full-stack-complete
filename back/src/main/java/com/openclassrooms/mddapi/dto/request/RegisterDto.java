package com.openclassrooms.mddapi.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterDto {
  @JsonProperty("email")
  @NotBlank
  @Email
  private String email;

  @JsonProperty("username")
  @NotBlank
  private String username;

  @JsonProperty("password")
  @NotBlank
  private String password;
}
