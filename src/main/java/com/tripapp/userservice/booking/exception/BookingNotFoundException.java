package com.tripapp.userservice.booking.exception;

/**
 * Thrown when a booking with a given ID does not exist.
 */
public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
