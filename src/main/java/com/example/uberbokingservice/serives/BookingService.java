package com.example.uberbokingservice.serives;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uberbokingservice.dtos.CreateBooking;
import com.example.uberbokingservice.dtos.CreateBookingResponse;

public interface BookingService {
    public CreateBookingResponse creatBooking(CreateBooking bookingDetails);
}
