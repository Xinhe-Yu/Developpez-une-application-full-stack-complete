package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.request.LoginDto;
import com.openclassrooms.mddapi.dto.request.RegisterDto;
import com.openclassrooms.mddapi.dto.request.UpdateDto;
import com.openclassrooms.mddapi.dto.response.ApiResponseDto;
import com.openclassrooms.mddapi.dto.response.ErrorResponseDto;
import com.openclassrooms.mddapi.dto.response.TokenResponseDto;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CustomUserDetails;
import com.openclassrooms.mddapi.services.CustomUserDetailsService;
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

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private UserMapper userMapper;

  @Operation(summary = "Create a new count", description = "Create a new count.")
  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponseDto> registerUser(@RequestBody RegisterDto userDto) {
    try {
      userService.validateAndSaveUser(userDto);

      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
      String token = jwtService.generateToken(authentication);
      TokenResponseDto response = new TokenResponseDto(token);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      e.printStackTrace();
      ErrorResponseDto response = new ErrorResponseDto("Échec inscription : " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @Operation(summary = "Login user", description = "Authenticate user with email and password and return JWT token")
  @PostMapping("/login")
  public ResponseEntity<ApiResponseDto> loginUser(@RequestBody LoginDto userDto) {
    try {
      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(userDto.getIdentifier(), userDto.getPassword()));
      String token = jwtService.generateToken(authentication);
      TokenResponseDto response = new TokenResponseDto(token);
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      ErrorResponseDto response = new ErrorResponseDto("Échec authentication");
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Get current user information", description = "Retrieve information about the currently authenticated user")
  @GetMapping("/me")
  public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetailsService.getCurrentUser(userDetails.getEmail());

    UserDto userDto = userMapper.toDto(user);
    return ResponseEntity.ok(userDto);
  }

  @Operation(summary = "Update current user information", description = "Updateinformation about the currently authenticated user")
  @PutMapping("/update")
  public ResponseEntity<ApiResponseDto> updateCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody UpdateDto userDto) {
    try {
      User updatedUser = userService.validateAndUpdateUser(userDto, userDetails);

      CustomUserDetails updatedUserDetails = CustomUserDetails.builder()
          .id(updatedUser.getId())
          .email(updatedUser.getEmail())
          .username(updatedUser.getUsername())
          .password(updatedUser.getPassword())
          .build();
      UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(updatedUserDetails, null,
          updatedUserDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(newAuth);

      String token = jwtService.generateToken(newAuth);
      TokenResponseDto response = new TokenResponseDto(token);
      return ResponseEntity.ok(response);
    } catch (ResponseStatusException e) {
      ErrorResponseDto response = new ErrorResponseDto("Échec modification : " +
          e.getReason());
      return new ResponseEntity<>(response, e.getStatusCode());
    } catch (Exception e) {
      ErrorResponseDto response = new ErrorResponseDto("Échec modification : " +
          e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }
}
