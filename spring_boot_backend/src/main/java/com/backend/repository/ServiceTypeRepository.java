package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.ServiceType;
import com.backend.entities.ServiceCategory;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    // Get all active services
    List<ServiceType> findByActiveTrue();

    // Get services by category
    List<ServiceType> findByCategory(ServiceCategory category);
}