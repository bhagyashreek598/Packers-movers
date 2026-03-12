package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Booking;
import com.backend.entities.Payment;
import com.backend.entities.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Get payments for a specific booking
    List<Payment> findByBooking(Booking booking);

    // Get payments by status
    List<Payment> findByPaymentStatus(PaymentStatus status);
}