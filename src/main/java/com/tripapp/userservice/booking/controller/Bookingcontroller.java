package com.tripapp.userservice.booking.controller;

import com.tripapp.userservice.booking.Booking;
import com.tripapp.userservice.booking.dto.BookingRequest;
import com.tripapp.userservice.booking.service.Bookingservice;
import com.tripapp.userservice.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class Bookingcontroller {

    private final Bookingservice bookingService;

    public Bookingcontroller(Bookingservice bookingService) {
        this.bookingService = bookingService;
    }

    // ✅ Create a new booking using userId from JWT
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest request,
                                                 Authentication authentication) {
        Long userId = extractUserId(authentication);
        Booking savedBooking = bookingService.createBooking(userId, request);
        return ResponseEntity.ok(savedBooking);
    }

    // ✅ Get all bookings (admin or dev only - ideally restrict later)
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // ✅ Test endpoint
    @GetMapping("/test")
    public String testBookingApi() {
        return "Booking controller is working";
    }

    // ✅ Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update booking (only if user owns it)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id,
                                           @Valid @RequestBody BookingRequest request,
                                           Authentication authentication) {
        Long userId = extractUserId(authentication);
        return bookingService.updateBooking(id, userId, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete booking by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // 🔐 Helper method to extract user ID from JWT principal
    private Long extractUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getId();
        } else {
            throw new IllegalArgumentException("Invalid authentication principal");
        }
    }
}
