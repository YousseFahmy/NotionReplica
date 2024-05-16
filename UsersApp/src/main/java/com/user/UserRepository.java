package com.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(UUID uuid);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    void deleteByUsername(String username);
}
