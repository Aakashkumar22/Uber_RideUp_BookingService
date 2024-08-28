package com.example.uberbokingservice.dtos;

import com.example.uber_rideupnewentityservice.Models.ExactLocation;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBooking {

    private Long passengerID;
    private ExactLocation startLocation;
    private ExactLocation endLocation;
}
