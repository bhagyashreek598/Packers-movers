package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Address;
import com.backend.entities.City;
import com.backend.entities.State;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
    // Find all addresses by state entity
    List<Address> findByState(State state);
    
    // Find all addresses by city entity
    List<Address> findByCity(City city);
    
    // Find addresses by state ID
    List<Address> findByStateId(Long stateId);
    
    // Find addresses by city ID
    List<Address> findByCityId(Long cityId);
}