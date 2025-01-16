package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public CustomUserDetails loadUserByUsername(String loginIdentifier) throws UsernameNotFoundException {
    return userRepository.findByEmailOrUsername(loginIdentifier, loginIdentifier)
        .map(user -> CustomUserDetails.builder()
            .id(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .password(user.getPassword())
            .build())
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
  }

  public User getCurrentUser(String loginIdentifier) {
    return userRepository.findByEmailOrUsername(loginIdentifier, loginIdentifier)
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
  }
}
