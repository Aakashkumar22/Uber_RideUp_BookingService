package com.example.uberbokingservice.dtos;

import com.example.uber_rideupnewentityservice.Models.BookingStatus;
import com.example.uber_rideupnewentityservice.Models.Driver;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingdto {
    private Long bookingId;
    private BookingStatus bookingStatus;
    private Optional<Driver>driver;
}
