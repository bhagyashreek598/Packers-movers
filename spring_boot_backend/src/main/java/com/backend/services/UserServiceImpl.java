package com.backend.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.AuthRequest;
import com.backend.dtos.AuthResponse;
import com.backend.dtos.UserRegisterRequest;
import com.backend.dtos.UserRegisterResponse;
import com.backend.entities.Customer;
import com.backend.entities.Staff;
import com.backend.entities.StaffRole;
import com.backend.entities.User;
import com.backend.entities.UserRole;
import com.backend.repository.CustomerRepository;
import com.backend.repository.StaffRepository;
import com.backend.repository.UserRepository;
import com.backend.security.JwtUtils;

import lombok.RequiredArgsConstructor;

import com.backend.exception.ApiException; // Added
import com.backend.exception.ResourceNotFoundException; // Added

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; // Changed from userRepo
    private final CustomerRepository customerRepo;
    private final StaffRepository staffRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final JwtUtils jwtUtils;

    @Override
    public UserRegisterResponse registerCustomer(UserRegisterRequest request) { // Changed return type and parameter name

        if (userRepository.existsByEmail(request.getEmail())) { // Changed userRepo to userRepository
            throw new ApiException("Email already exists!"); // Changed RuntimeException to ApiException
        }
        
        if (request.getStaffType() != null) { // Changed request to dto
            throw new ApiException("staffType is not allowed for customer registration"); // Changed RuntimeException to ApiException
        }

        Customer customer = mapper.map(request, Customer.class); // Changed request to dto

        customer.setPassword(passwordEncoder.encode(request.getPassword())); // Changed request to dto
        customer.setUserRole(UserRole.CUSTOMER); // 🔒 default role
        //customer.setActive(true);

        Customer savedCustomer = customerRepo.save(customer); // Kept customerRepo as it's not explicitly removed in diff

        return new UserRegisterResponse(
            savedCustomer.getId(),
            savedCustomer.getName(),
            savedCustomer.getEmail(),
            "Registration Successful!"
        );
    }

    @Override
    public UserRegisterResponse registerStaff(UserRegisterRequest request) { // Changed return type and parameter name

        if (userRepository.existsByEmail(request.getEmail())) { // Changed userRepo to userRepository
            throw new ApiException("Email already exists"); // Changed RuntimeException to ApiException
        }

        if (request.getStaffType() == null) { // Changed request to dto
            throw new ApiException("Staff type is required for staff registration"); // Changed RuntimeException to ApiException
        }

        Staff staff = mapper.map(request, Staff.class); // Changed request to dto

        staff.setPassword(passwordEncoder.encode(request.getPassword())); // Changed request to dto
        staff.setUserRole(UserRole.STAFF);   // 🔒 backend control
        staff.setActive(true);

        staff.setStaffRole(
            StaffRole.valueOf(request.getStaffType().toUpperCase()) // Changed request to dto
        );

        Staff savedStaff = userRepository.save(staff); // Changed userRepo to userRepository


        return new UserRegisterResponse(
            savedStaff.getId(),
            savedStaff.getName(),
            savedStaff.getEmail(),
            "Staff registered successfully"
        );
    }



    @Override
    public AuthResponse authenticateUser(AuthRequest request) {
        // 1. find user by email
        User user = userRepository.findByEmail(request.getEmail()) // Changed userRepo to userRepository
                .orElseThrow(() -> new ApiException("Invalid Email or Password")); // Changed RuntimeException to ApiException

        // 2. match password (use matches method, not equals !)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid Email or Password"); // Changed RuntimeException to ApiException
        }

        // 3. if matches , then  generate JWT Token
        String jwtToken = jwtUtils.generateToken(user); 

        return new AuthResponse(jwtToken, user.getName(), user.getUserRole().toString(), "Login Success!");
    }

    @Override
    public UserRegisterResponse updateProfile(String emailOrId, com.backend.dtos.UserUpdateDTO request) {
        User user;
        try {
            Long id = Long.parseLong(emailOrId);
            user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        } catch (NumberFormatException e) {
            user = userRepository.findByEmail(emailOrId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + emailOrId));
        }
        
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        
        User updatedUser = userRepository.save(user);
        
        return new UserRegisterResponse(
            updatedUser.getId(),
            updatedUser.getName(),
            updatedUser.getEmail(),
            "Profile updated successfully!"
        );
    }
}