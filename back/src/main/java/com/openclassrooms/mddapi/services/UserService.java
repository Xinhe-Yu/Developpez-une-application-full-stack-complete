package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.request.RegisterDto;
import com.openclassrooms.mddapi.dto.request.UpdateDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User getUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    return user;
  }

  public void validateAndUpdateUser(UpdateDto userDto) {
    User user = userRepository.findById(userDto.getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    user.setUsername(userDto.getUsername());
    user.setEmail(userDto.getEmail());

    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
      if (!validatePassword(userDto.getPassword())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
      }

      user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }

    userRepository.save(user);
  }

  public void validateAndSaveUser(RegisterDto userDto) {
    if (!validatePassword(userDto.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
    } else if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
    } else if (userRepository.existsByUsername(userDto.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
    }

    User user = new User(userDto.getEmail(),
        userDto.getUsername(),
        passwordEncoder.encode(userDto.getPassword()));

    userRepository.save(user);
  }

  public boolean validatePassword(String password) {
    boolean hasUppercase = !password.equals(password.toLowerCase());
    boolean hasLowercase = !password.equals(password.toUpperCase());
    boolean hasNumber = password.matches(".*\\d.*");
    boolean hasSpecialChar = !password.matches("[A-Za-z0-9 ]*");
    boolean hasMinimumLength = password.length() >= 8;

    return hasUppercase && hasLowercase && hasNumber && hasSpecialChar && hasMinimumLength;
  }

}
