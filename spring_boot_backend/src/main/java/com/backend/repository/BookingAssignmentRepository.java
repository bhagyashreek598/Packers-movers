package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Booking;
import com.backend.entities.BookingAssignment;
import com.backend.entities.Staff;
import com.backend.entities.Vehicle;

public interface BookingAssignmentRepository extends JpaRepository<BookingAssignment, Long> {

    // Get assignments for a specific booking
    List<BookingAssignment> findByBooking(Booking booking);

    // Get assignments for a specific staff member
    List<BookingAssignment> findByStaff(Staff staff);

    // Get assignments for a specific vehicle
    List<BookingAssignment> findByVehicle(Vehicle vehicle);
}