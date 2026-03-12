package com.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.backend.dtos.BookingStatusUpdateRequest;
import com.backend.dtos.UserRegisterRequest;
import com.backend.dtos.UserUpdateDTO;
import com.backend.services.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
//@CrossOrigin(origins = "http://localhost:5174")
//@CrossOrigin(origins = "https://kd4-vaibhavi-92455.github.io")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // 🔒 ADMIN only
public class AdminController {

    private final AdminService adminService;

    // ==================== BOOKING MANAGEMENT ====================

    @GetMapping("/bookings")
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(adminService.getAllBookings());
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getBookingById(id));
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<?> updateBooking(
            @PathVariable Long id, 
            @RequestBody com.backend.dtos.BookingRequest request) {
        return ResponseEntity.ok(adminService.updateBooking(id, request));
    }

    @PutMapping("/bookings/{id}/status")
    public ResponseEntity<?> updateBookingStatus(
            @PathVariable Long id, 
            @RequestBody BookingStatusUpdateRequest request) {
        adminService.updateBookingStatus(id, request.getStatus());
        return ResponseEntity.ok("Booking status updated to " + request.getStatus());
    }

    // ==================== STAFF MANAGEMENT ====================

    @GetMapping("/staff")
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(adminService.getAllStaff());
    }

    @GetMapping("/staff/inactive")
    public ResponseEntity<?> getInactiveStaff() {
        return ResponseEntity.ok(adminService.getInactiveStaff());
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getStaffById(id));
    }

    @PostMapping("/staff/register")
    public ResponseEntity<?> registerStaff(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(adminService.addStaff(request));
    }

    @PutMapping("/staff/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody UserUpdateDTO request) {
        return ResponseEntity.ok(adminService.updateStaff(id, request));
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        adminService.deleteStaff(id);
        return ResponseEntity.ok("Staff member removed successfully");
    }

    // ==================== CUSTOMER MANAGEMENT ====================

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(adminService.addCustomer(request));
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(adminService.getAllCustomers());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getCustomerById(id));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody UserUpdateDTO request) {
        return ResponseEntity.ok(adminService.updateCustomer(id, request));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        adminService.deleteCustomer(id);
        return ResponseEntity.ok("Customer removed successfully");
    }
}
