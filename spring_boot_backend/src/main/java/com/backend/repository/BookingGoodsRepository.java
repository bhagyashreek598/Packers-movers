package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Booking;
import com.backend.entities.BookingGoods;
import com.backend.entities.GoodsMaster;

public interface BookingGoodsRepository extends JpaRepository<BookingGoods, Long> {

    // Get goods associated with a booking
    List<BookingGoods> findByBooking(Booking booking);

    // Check if a specific good is part of a booking
    boolean existsByBookingAndGoods(Booking booking, GoodsMaster goods);
}