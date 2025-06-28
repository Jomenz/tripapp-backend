package com.tripapp.userservice.booking;


import jakarta.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class BookingDetails {

    private String hotelName;
    private LocalDate checkIn;
    private LocalDate checkOut;

    // Getters and Setters
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
}
