package com.example.uberbokingservice.Repository;

import com.example.uber_rideupnewentityservice.Models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository  extends JpaRepository<Passenger ,Long> {

}
