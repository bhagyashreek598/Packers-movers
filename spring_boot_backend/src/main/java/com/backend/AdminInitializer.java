package com.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.entities.Staff;
import com.backend.entities.StaffRole;
import com.backend.entities.UserRole;
import com.backend.repository.StaffRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final StaffRepository staffRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.name}")
    private String name;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.phone}")
    private String phone;

    @Value("${app.admin.password}")
    private String password;

    @Override
    public void run(String... args) {

        if (!staffRepo.existsByEmail(email)) {

            Staff admin = new Staff();
            admin.setName(name);
            admin.setEmail(email);
            admin.setPhone(phone);
            admin.setPassword(passwordEncoder.encode(password));

            admin.setUserRole(UserRole.ADMIN);   // 🔥 IMPORTANT
            admin.setStaffRole(StaffRole.ADMIN); // 🔥 Set StaffRole
            admin.setActive(true);

            staffRepo.save(admin);

            System.out.println("✅ ADMIN user created successfully");
        }
    }
}
