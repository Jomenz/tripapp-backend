package com.tripapp.userservice.booking.service;

import com.tripapp.userservice.booking.Booking;
import com.tripapp.userservice.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Bookingservice {

    private final BookingRepository bookingRepository;

    public Bookingservice(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Create or update a booking
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Delete booking by ID
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
