package com.tripapp.userservice.booking.repository;

import com.tripapp.userservice.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Checks if a booking already exists with the same user, service type, and amount
    boolean existsByUserIdAndServiceTypeAndAmount(Long userId, String serviceType, BigDecimal amount);


}
