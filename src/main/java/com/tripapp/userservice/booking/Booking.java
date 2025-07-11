package com.tripapp.userservice.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String serviceType; // e.g., "flight", "hotel", "car", "activity"

    @Embedded
    private BookingDetails bookingDetails; // ✅ Now stored in separate columns

    private BigDecimal amount;

    private String status; // e.g., "PENDING", "CONFIRMED", "CANCELLED"
}
