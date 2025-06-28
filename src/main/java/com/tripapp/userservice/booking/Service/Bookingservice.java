package com.tripapp.userservice.booking.service;

import com.tripapp.userservice.booking.Booking;
import com.tripapp.userservice.booking.BookingDetails;
import com.tripapp.userservice.booking.dto.BookingDetailsDTO;
import com.tripapp.userservice.booking.dto.BookingRequest;
import com.tripapp.userservice.booking.exception.BookingNotFoundException;
import com.tripapp.userservice.booking.exception.DuplicateBookingException;
import com.tripapp.userservice.booking.exception.InvalidBookingInputException;
import com.tripapp.userservice.booking.repository.BookingRepository;
import com.tripapp.userservice.exception.UserNotFoundException;
import com.tripapp.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Bookingservice {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public Bookingservice(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Booking createBooking(Long userId, BookingRequest request) {
        // Ensure the user exists
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        // Prevent duplicate booking
        boolean exists = bookingRepository.existsByUserIdAndServiceTypeAndAmount(
                userId,
                request.getServiceType(),
                request.getAmount()
        );
        if (exists) {
            throw new DuplicateBookingException("Duplicate booking detected for this user, service type, and amount");
        }

        // Map request to entity
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setServiceType(request.getServiceType());
        booking.setAmount(request.getAmount());
        booking.setStatus(request.getStatus());

        try {
            BookingDetails embeddedDetails = mapToEmbedded(request.getBookingDetails());
            booking.setBookingDetails(embeddedDetails);
        } catch (Exception e) {
            throw new InvalidBookingInputException("Invalid booking details format");
        }

        return bookingRepository.save(booking);
    }

    @Transactional
    public Optional<Booking> updateBooking(Long id, Long userId, BookingRequest request) {
        return bookingRepository.findById(id).map(existing -> {
            // Ensure booking belongs to the user
            if (!existing.getUserId().equals(userId)) {
                throw new BookingNotFoundException("You do not have permission to update this booking.");
            }

            existing.setServiceType(request.getServiceType());
            existing.setAmount(request.getAmount());
            existing.setStatus(request.getStatus());

            try {
                BookingDetails embeddedDetails = mapToEmbedded(request.getBookingDetails());
                existing.setBookingDetails(embeddedDetails);
            } catch (Exception e) {
                throw new InvalidBookingInputException("Invalid booking details format");
            }

            return bookingRepository.save(existing);
        });
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException("Booking with ID " + id + " not found");
        }
        bookingRepository.deleteById(id);
    }

    private BookingDetails mapToEmbedded(BookingDetailsDTO dto) {
        BookingDetails details = new BookingDetails();
        details.setHotelName(dto.getHotelName());
        details.setCheckIn(dto.getCheckIn());
        details.setCheckOut(dto.getCheckOut());
        return details;
    }
}
