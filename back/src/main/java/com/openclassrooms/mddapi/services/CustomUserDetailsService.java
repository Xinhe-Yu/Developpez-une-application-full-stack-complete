package com.openclassrooms.mddapi.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    ;

    return CustomUserDetails.builder()
        .id(user.getId())
        .email(user.getEmail())
        .username(user.getUsername())
        .password(user.getPassword())
        .build();
  }

  public User getCurrentUser(String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return user;
  }

}
