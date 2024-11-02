package com.easyride.user_service;

import java.util.Optional;

import com.easyride.user_service.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}

