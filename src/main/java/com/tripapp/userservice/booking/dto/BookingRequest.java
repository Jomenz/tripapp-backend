package com.tripapp.userservice.booking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingRequest {

    @NotBlank(message = "Service type is required")
    private String serviceType;

    @NotNull(message = "Booking details are required")
    @Valid
    private BookingDetailsDTO bookingDetails;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Booking status is required")
    private String status;
}
