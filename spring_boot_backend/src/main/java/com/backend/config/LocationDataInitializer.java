package com.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entities.City;
import com.backend.entities.State;
import com.backend.repository.CityRepository;
import com.backend.repository.StateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationDataInitializer implements CommandLineRunner {

    private final StateRepository stateRepo;
    private final CityRepository cityRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (stateRepo.count() > 0) {
            log.info("State and City data already exists. Skipping initialization.");
            return;
        }

        log.info("Initializing State and City data...");

        // Create Maharashtra
        State maharashtra = new State("Maharashtra");
        maharashtra = stateRepo.save(maharashtra);
        
        cityRepo.save(new City("Mumbai", maharashtra));
        cityRepo.save(new City("Pune", maharashtra));
        cityRepo.save(new City("Nagpur", maharashtra));

        // Create Madhya Pradesh
        State madhyaPradesh = new State("Madhya Pradesh");
        madhyaPradesh = stateRepo.save(madhyaPradesh);
        
        cityRepo.save(new City("Bhopal", madhyaPradesh));
        cityRepo.save(new City("Indore", madhyaPradesh));
        cityRepo.save(new City("Gwalior", madhyaPradesh));

        log.info("State and City data initialized successfully.");
        log.info("Total States: {}", stateRepo.count());
        log.info("Total Cities: {}", cityRepo.count());
    }
}
