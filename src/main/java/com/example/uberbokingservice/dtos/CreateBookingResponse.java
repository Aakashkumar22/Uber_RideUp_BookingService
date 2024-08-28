package com.example.uberbokingservice.dtos;

import com.example.uber_rideupnewentityservice.Models.Driver;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResponse {
    private Long bookingID;
    private String BookingStatus;
    private Optional<Driver> driver;
}
