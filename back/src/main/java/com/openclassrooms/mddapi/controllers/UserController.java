package com.openclassrooms.mddapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "auth", description = "User API")
public class UserController {
  // @Autowired
  // private CustomUserDetailsService userDetailsService;

  // @Autowired
  // private UserMapper userMapper;

  // @Autowired
  // private UserService userService;

  // @Operation(summary = "Get current user information", description = "Retrieve
  // information about the currently authenticated user")
  // @GetMapping("/me")
  // public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal
  // UserDetails userDetails) {
  // User user = userDetailsService.getCurrentUser(userDetails.getUsername());
  // ;
  // UserDto userDto = userMapper.convertToDTO(user);
  // return ResponseEntity.ok(userDto);
  // }

  // @Operation(summary = "Update current user information", description = "Update
  // information about the currently authenticated user")
  // @PutMapping("/me")
  // public ResponseEntity<ApiResponseDto>
  // updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails,
  // @RequestBody UpdateDto userDto) {
  // try {
  // userService.validateAndUpdateUser(userDto);
  // MsgResponseDto response = new MsgResponseDto("User updated");
  // return ResponseEntity.ok(response);
  // } catch (Exception e) {
  // ErrorResponseDto response = new ErrorResponseDto("Update failed: " +
  // e.getMessage());
  // return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  // }
  // }
}
