package com.example.uberbokingservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocationdto {
    String driverId;
    Double latitude;
    Double longitude;
}
