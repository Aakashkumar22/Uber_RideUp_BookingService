package com.example.uberbokingservice.dtos;


import com.example.uberbokingservice.Models.ExactLocation;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RideRequestDto {
    private Long bookingID;
    private Long passengerID;
    private ExactLocation startLocation;
    private ExactLocation endLocation;
    private List<Long> driverIds;

}
