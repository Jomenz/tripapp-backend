package com.tripapp.userservice.booking.repository;


import com.tripapp.userservice.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Optional: Add custom queries here later
}
