package com.tripapp.userservice.booking.exception;



public class DuplicateBookingException extends RuntimeException {

    /**
     * Constructs a new DuplicateBookingException with the specified detail message.
     *
     * @param message the detail message
     */
    public DuplicateBookingException(String message) {
        super(message);
    }
}
