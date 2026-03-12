package com.backend.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Check if user exists by email or phone
    boolean existsByEmail(String email);

    // Sign-in: find user by email and password
    Optional<User> findByEmailAndPassword(String email, String password);

    // Find by email (for authentication / email uniqueness check)
    Optional<User> findByEmail(String email);

    // Get all users with a specific name
    List<User> findByName(String name);
}