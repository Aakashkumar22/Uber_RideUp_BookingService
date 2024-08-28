package com.example.uberbokingservice.Repository;

import com.example.uber_rideupnewentityservice.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking ,Long> {
}
