package com.tripapp.userservice.booking.controller;

import com.tripapp.userservice.booking.Booking;
import com.tripapp.userservice.booking.service.Bookingservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class Bookingcontroller {

    private final Bookingservice bookingService;

    public Bookingcontroller(Bookingservice bookingService) {
        this.bookingService = bookingService;
    }

    // Create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(savedBooking);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        return bookingService.getBookingById(id)
                .map(existingBooking -> {
                    existingBooking.setServiceType(updatedBooking.getServiceType());
                    existingBooking.setBookingDetails(updatedBooking.getBookingDetails());
                    existingBooking.setAmount(updatedBooking.getAmount());
                    existingBooking.setStatus(updatedBooking.getStatus());
                    existingBooking.setUserId(updatedBooking.getUserId());
                    Booking saved = bookingService.createBooking(existingBooking);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete booking by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

