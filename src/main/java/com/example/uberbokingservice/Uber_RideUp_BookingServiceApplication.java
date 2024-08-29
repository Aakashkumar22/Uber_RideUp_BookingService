package com.example.uberbokingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.example.uber_rideupnewentityservice.Models")
public class Uber_RideUp_BookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Uber_RideUp_BookingServiceApplication.class, args);
    }

}
