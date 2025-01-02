package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.request.LoginDto;
import com.openclassrooms.mddapi.dto.request.RegisterDto;
import com.openclassrooms.mddapi.dto.response.ApiResponseDto;
import com.openclassrooms.mddapi.dto.response.ErrorResponseDto;
import com.openclassrooms.mddapi.dto.response.TokenResponseDto;
import com.openclassrooms.mddapi.services.JWTService;
import com.openclassrooms.mddapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth", description = "Authentication API")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JWTService jwtService;

  @Autowired
  private UserService userService;

  @Operation(summary = "Create a new count", description = "Create a new count.")
  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponseDto> registerUser(@RequestBody RegisterDto userDto) {
    try {
      System.out.println("DTO: " + userDto);

      userService.validateAndSaveUser(userDto);

      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
      String token = jwtService.generateToken(authentication);
      TokenResponseDto response = new TokenResponseDto(token);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      e.printStackTrace();
      ErrorResponseDto response = new ErrorResponseDto("Registration failed: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @Operation(summary = "Login user", description = "Authenticate user with email and password and return JWT token")
  @PostMapping("/login")
  public ResponseEntity<ApiResponseDto> loginUser(@RequestBody LoginDto userDto) {
    try {
      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
      String token = jwtService.generateToken(authentication);
      TokenResponseDto response = new TokenResponseDto(token);
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      ErrorResponseDto response = new ErrorResponseDto("Authentication failed");
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
  }
}
