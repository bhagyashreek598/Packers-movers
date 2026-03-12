package com.backend.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.backend.dtos.AuthRequest;
import com.backend.dtos.AuthResponse;
import com.backend.dtos.BookingDetailsResponse;
import com.backend.dtos.BookingRequest;
import com.backend.dtos.CancellationRequestDTO;
import com.backend.dtos.UserRegisterRequest;
import com.backend.entities.User;
import com.backend.security.JwtUtils;
import com.backend.security.TokenBlacklistService;
import com.backend.security.UserPrincipal;
import com.backend.services.BookingService;
import com.backend.services.LocationService;
import com.backend.services.UserService;

import lombok.RequiredArgsConstructor;

//@CrossOrigin(origins = "http://localhost:5174")
//@CrossOrigin(origins = "https://kd4-vaibhavi-92455.github.io")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;
    private final LocationService locationService;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/signup/customer")
    public ResponseEntity<?> signupCustomer(@RequestBody @Valid UserRegisterRequest request) {
        return ResponseEntity.ok(userService.registerCustomer(request));
    }

    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid AuthRequest request) {
        // 1. Authenticate user
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());
        
        Authentication authenticatedDetails = authManager.authenticate(authToken);
        
        UserPrincipal principal = (UserPrincipal) authenticatedDetails.getPrincipal();
        
        // 3. Generate Token
        String jwtToken = jwtUtils.generateToken(principal);
        
        // 4. Return AuthResponse 
        return ResponseEntity.ok(new AuthResponse(
                jwtToken,                       // jwt
                principal.getUsername(),        // email
                principal.getRoleName(),        // role
                "Successful Login"              // message
        ));
    }

    /**
     * Logout endpoint — blacklists the current JWT so it cannot be reused.
     * On next login, a brand new token is generated.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7).trim();
            // Parse claims to get expiry & subject, then blacklist the token
            io.jsonwebtoken.Claims claims = jwtUtils.validateToken(jwt);
            tokenBlacklistService.blacklist(jwt, claims);
        }

        return ResponseEntity.ok(java.util.Map.of("message", "Logged out successfully. Please login again for a new session."));
    }

    // ==================== BOOKING ENDPOINTS ====================
    
    /**
     * Create a new booking with PENDING status
     */
    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(
            @RequestBody @Valid BookingRequest request,
            Authentication authentication) {
        
        String userEmail = authentication.getName();
        BookingDetailsResponse response = bookingService.createBooking(request, userEmail);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Update an existing booking (only if status is PENDING)
     */
    @PutMapping("/bookings/{id}")
    public ResponseEntity<?> updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid BookingRequest request,
            Authentication authentication) {
        
        String userEmail = authentication.getName();
        return ResponseEntity.ok(bookingService.updateBooking(id, request, userEmail));
    }
    
    /**
     * Get all current (active) bookings for the logged-in user
     */
    @GetMapping("/bookings/current")
    public ResponseEntity<?> getCurrentBookings(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(bookingService.getCurrentBookings(userEmail));
    }
    
    /**
     * Get booking history (delivered/cancelled) for the logged-in user
     */
    @GetMapping("/bookings/history")
    public ResponseEntity<?> getBookingHistory(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(bookingService.getBookingHistory(userEmail));
    }
    
    /**
     * Cancel a booking - only allowed if status is PENDING
     */
    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<?> cancelBooking(
            @PathVariable Long id,
            Authentication authentication) {
        
        String userEmail = authentication.getName();
        return ResponseEntity.ok(bookingService.cancelBooking(id, userEmail));
    }
    
    /**
     * Request cancellation for non-PENDING bookings (sends email to admin)
     */
    @PostMapping("/bookings/{id}/request-cancellation")
    public ResponseEntity<?> requestCancellation(
            @PathVariable Long id,
            @RequestBody @Valid CancellationRequestDTO request,
            Authentication authentication) {
        
        String userEmail = authentication.getName();
        String message = bookingService.requestCancellation(id, userEmail, request.getReason());
        return ResponseEntity.ok(message);
    }

    // ==================== LOCATION ENDPOINTS ====================
    
    /**
     * Get all available state names for dropdown
     */
    @GetMapping("/locations/states")
    public ResponseEntity<?> getAllStates() {
        return ResponseEntity.ok(locationService.getAllStates());
    }
    
    /**
     * Get city names by state name for cascading dropdown
     */
    @GetMapping("/locations/states/{stateName}/cities")
    public ResponseEntity<?> getCitiesByState(@PathVariable String stateName) {
        return ResponseEntity.ok(locationService.getCitiesByState(stateName));
    }

    /**
     * Update logged-in user's profile (name, phone)
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid com.backend.dtos.UserUpdateDTO request, Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.updateProfile(email, request));
    }

    }