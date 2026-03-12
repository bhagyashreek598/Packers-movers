package com.backend.services;

import java.util.List;

import com.backend.dtos.BookingDetailsResponse;
import com.backend.dtos.UserDTO;
import com.backend.dtos.UserUpdateDTO;
import com.backend.entities.BookingStatus;

public interface AdminService {
    
    // Booking Management
    List<BookingDetailsResponse> getAllBookings();
    BookingDetailsResponse getBookingById(Long bookingId);
    BookingDetailsResponse updateBooking(Long bookingId, com.backend.dtos.BookingRequest request);
    void updateBookingStatus(Long bookingId, BookingStatus status);
    
    // Staff Management
    List<UserDTO> getAllStaff();
    List<UserDTO> getInactiveStaff();
    UserDTO getStaffById(Long staffId);
    UserDTO addStaff(com.backend.dtos.UserRegisterRequest request);
    UserDTO updateStaff(Long staffId, UserUpdateDTO request);
    void deleteStaff(Long staffId);
    
    // Customer Management
    UserDTO addCustomer(com.backend.dtos.UserRegisterRequest request);
    List<UserDTO> getAllCustomers();
    UserDTO getCustomerById(Long customerId);
    UserDTO updateCustomer(Long customerId, UserUpdateDTO request);
    void deleteCustomer(Long customerId);
}
