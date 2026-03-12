package com.backend.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entities.ServiceCategory;
import com.backend.entities.ServiceType;
import com.backend.repository.ServiceTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceDataInitializer implements CommandLineRunner {

    private final ServiceTypeRepository serviceTypeRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (serviceTypeRepo.count() > 0) {
            log.info("Service data already exists. Skipping initialization.");
            return;
        }

        log.info("Initializing Service data...");

        // Home Service
        ServiceType homeService = new ServiceType();
        homeService.setName("Premium Home Relocation");
        homeService.setCategory(ServiceCategory.HOME);
        homeService.setBasePrice(new BigDecimal("5000.00"));
        homeService.setActive(true);
        serviceTypeRepo.save(homeService);

        // Office Service
        ServiceType officeService = new ServiceType();
        officeService.setName("Corporate Office Shifting");
        officeService.setCategory(ServiceCategory.OFFICE);
        officeService.setBasePrice(new BigDecimal("15000.00"));
        officeService.setActive(true);
        serviceTypeRepo.save(officeService);

        // Vehicle Service
        ServiceType vehicleService = new ServiceType();
        vehicleService.setName("Safe Vehicle Transport");
        vehicleService.setCategory(ServiceCategory.VEHICLE);
        vehicleService.setBasePrice(new BigDecimal("8000.00"));
        vehicleService.setActive(true);
        serviceTypeRepo.save(vehicleService);

        log.info("Service data initialized successfully.");
    }
}
