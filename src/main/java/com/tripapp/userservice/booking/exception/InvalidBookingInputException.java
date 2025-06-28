package com.tripapp.userservice.booking.exception;

/**
 * Thrown when booking input validation fails (e.g., invalid dates, missing fields, etc.)
 */
public class InvalidBookingInputException extends RuntimeException {


    public InvalidBookingInputException(String message) {
        super(message);
    }
}
