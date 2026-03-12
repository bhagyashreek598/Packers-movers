package com.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.State;

public interface StateRepository extends JpaRepository<State, Long> {
    
    // Find state by name
    Optional<State> findByName(String name);
    
    // Check if state exists by name
    boolean existsByName(String name);
}
