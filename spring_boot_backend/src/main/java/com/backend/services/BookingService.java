package com.backend.services;

import java.util.List;

import com.backend.dtos.BookingDetailsResponse;
import com.backend.dtos.BookingRequest;
import com.backend.dtos.BookingResponse;

public interface BookingService {
    
    /**
     * Create a new booking with PENDING status
     * @param request Booking details
     * @param userEmail Authenticated user's email
     * @return Created booking details
     */
    BookingDetailsResponse createBooking(BookingRequest request, String userEmail);
    
    /**
     * Get all current (active) bookings for the user
     * Current bookings = NOT (DELIVERED or CANCELLED)
     * @param userEmail Authenticated user's email
     * @return List of current bookings
     */
    List<BookingDetailsResponse> getCurrentBookings(String userEmail);
    
    /**
     * Update an existing booking (only if status is PENDING)
     * @param bookingId ID of the booking to update
     * @param request New booking details
     * @param userEmail Authenticated user's email
     * @return Updated booking details
     */
    BookingDetailsResponse updateBooking(Long bookingId, BookingRequest request, String userEmail);
    
    /**
     * Get booking history for the user
     * History = DELIVERED or CANCELLED bookings
     * @param userEmail Authenticated user's email
     * @return List of historical bookings
     */
    List<BookingDetailsResponse> getBookingHistory(String userEmail);
    
    /**
     * Cancel a booking - only allowed if status is PENDING
     * @param bookingId Booking ID to cancel
     * @param userEmail Authenticated user's email
     * @return Cancellation response
     */
    BookingResponse cancelBooking(Long bookingId, String userEmail);
    
    /**
     * Request cancellation via email for non-PENDING bookings
     * @param bookingId Booking ID
     * @param userEmail Authenticated user's email
     * @param reason Reason for cancellation
     * @return Success message
     */
    String requestCancellation(Long bookingId, String userEmail, String reason);
}
