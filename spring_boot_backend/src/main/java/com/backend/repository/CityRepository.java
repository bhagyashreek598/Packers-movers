package com.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.City;

public interface CityRepository extends JpaRepository<City, Long> {
    
    // Find all cities by state ID
    List<City> findByStateId(Long stateId);
    
    // Find city by ID and state ID (for validation)
    Optional<City> findByIdAndStateId(Long cityId, Long stateId);
    
    // Check if city exists in a state
    boolean existsByIdAndStateId(Long cityId, Long stateId);
}
