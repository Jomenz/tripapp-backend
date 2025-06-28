package com.tripapp.userservice.booking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.tripapp.userservice.booking")
public class BookingErrorHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBookingNotFound(BookingNotFoundException ex) {
        return errorResponse("Booking not found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateBookingException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateBooking(DuplicateBookingException ex) {
        return errorResponse("Duplicate booking", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidBookingInputException.class)
    public ResponseEntity<Map<String, String>> handleInvalidBooking(InvalidBookingInputException ex) {
        return errorResponse("Invalid input", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return errorResponse("Something went wrong", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> errorResponse(String error, String details, HttpStatus status) {
        Map<String, String> body = new HashMap<>();
        body.put("error", error);
        body.put("details", details);
        return new ResponseEntity<>(body, status);
    }
}
