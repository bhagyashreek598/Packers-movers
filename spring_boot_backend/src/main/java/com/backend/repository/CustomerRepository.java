package com.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Check if a customer exists by phone or email
    boolean existsByPhoneOrEmail(String phone, String email);

    // Get customer details by phone
    Optional<Customer> findByPhone(String phone);

    // Get customer details by email
    Optional<Customer> findByEmail(String email);
}