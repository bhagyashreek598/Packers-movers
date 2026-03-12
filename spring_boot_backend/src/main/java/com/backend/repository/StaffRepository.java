package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Staff;
import com.backend.entities.StaffRole;
import com.backend.entities.UserRole;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    // Get all active staff
    List<Staff> findByActiveTrue();

    // Get all inactive staff
    List<Staff> findByActiveFalse();

    // Get staff by role
    List<Staff> findByStaffRole(StaffRole staffRole);

	boolean existsByEmail(String email);

}