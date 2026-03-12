package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // Get all active vehicles
    List<Vehicle> findByActiveTrue();

    // Check if a vehicle exists by number plate
    boolean existsByNumberPlate(String numberPlate);
}