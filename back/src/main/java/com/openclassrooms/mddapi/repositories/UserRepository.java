package com.openclassrooms.mddapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByEmailOrUsername(String email, String username);

  Boolean existsByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmailAndIdNot(String email, Long id);

  Boolean existsByUsernameAndIdNot(String username, Long id);
}
