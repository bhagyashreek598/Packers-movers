package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.GoodsMaster;

public interface GoodsMasterRepository extends JpaRepository<GoodsMaster, Long> {
    // Get all active goods
    List<GoodsMaster> findByActiveTrue();

    // Check if a good exists by name and category
    boolean existsByNameAndCategory(String name, String category);
}