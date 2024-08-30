package com.example.uberbokingservice.serives;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uberbokingservice.dtos.CreateBooking;
import com.example.uberbokingservice.dtos.CreateBookingResponse;
import com.example.uberbokingservice.dtos.UpdateBookingRequestdto;
import com.example.uberbokingservice.dtos.UpdateBookingdto;

public interface BookingService {
    public CreateBookingResponse creatBooking(CreateBooking bookingDetails);
    public UpdateBookingdto updateBooking(UpdateBookingRequestdto updateBookingDetails,Long bookingId);
}
