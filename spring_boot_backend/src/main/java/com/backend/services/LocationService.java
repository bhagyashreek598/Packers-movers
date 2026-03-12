package com.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.dtos.CityDTO;
import com.backend.dtos.StateDTO;
import com.backend.entities.City;
import com.backend.entities.State;
import com.backend.repository.CityRepository;
import com.backend.repository.StateRepository;

import lombok.RequiredArgsConstructor;

import com.backend.exception.ApiException;
import com.backend.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final StateRepository stateRepo;
    private final CityRepository cityRepo;

    /**
     * Get all available state names
     * @return List of strings
     */
    public List<String> getAllStates() {
        return stateRepo.findAll().stream()
            .map(State::getName)
            .collect(Collectors.toList());
    }

    /**
     * Get city names by state name
     * @param stateName Name of the state
     * @return List of strings
     */
    public List<String> getCitiesByState(String stateName) {
        State state = stateRepo.findByName(stateName)
            .orElseThrow(() -> new ResourceNotFoundException("State '" + stateName + "' not found"));

        return cityRepo.findByStateId(state.getId()).stream()
            .map(City::getName)
            .collect(Collectors.toList());
    }

    /**
     * Validate if city belongs to state (String based)
     * @param cityName Name of the city
     * @param stateName Name of the state
     */
    public void validateCityInState(String cityName, String stateName) {
        State state = stateRepo.findByName(stateName)
            .orElseThrow(() -> new ResourceNotFoundException("State '" + stateName + "' not found"));

        List<String> cities = getCitiesByState(stateName);
        if (!cities.contains(cityName)) {
            throw new ApiException(
                "City '" + cityName + "' does not belong to State '" + stateName + "'. " +
                "Available cities: " + String.join(", ", cities)
            );
        }
    }

    /**
     * Get state by name
     */
    public State getStateByName(String name) {
        return stateRepo.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("State '" + name + "' not found"));
    }

    /**
     * Get city by name and state ID
     */
    public City getCityByNameAndState(String cityName, Long stateId) {
        return cityRepo.findByStateId(stateId).stream()
            .filter(c -> c.getName().equalsIgnoreCase(cityName))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("City '" + cityName + "' not found in selected state"));
    }
}
