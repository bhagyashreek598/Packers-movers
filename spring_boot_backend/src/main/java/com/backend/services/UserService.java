package com.backend.services;

import com.backend.dtos.AuthRequest;
import com.backend.dtos.AuthResponse;
import com.backend.dtos.UserRegisterRequest;
import com.backend.dtos.UserRegisterResponse;

public interface UserService {
    // Customer registration
    UserRegisterResponse registerCustomer(UserRegisterRequest request);

    // Staff (Driver, Manager, Helper) registration
    UserRegisterResponse registerStaff(UserRegisterRequest request);
    AuthResponse authenticateUser(AuthRequest request);
    
    // Update user profile (name, phone)
    UserRegisterResponse updateProfile(String emailOrId, com.backend.dtos.UserUpdateDTO request);

    // Default method to log user activity without changing existing implementing classes
    default void logUserActivity(String email, String activity) {
        System.out.println("User Activity for email " + email + ": " + activity);
    }
}