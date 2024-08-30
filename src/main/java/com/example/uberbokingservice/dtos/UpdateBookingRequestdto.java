package com.example.uberbokingservice.dtos;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uber_rideupnewentityservice.Models.BookingStatus;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingRequestdto {
    private BookingStatus bookingStatus;
    private Optional<Long> driverId;
}
