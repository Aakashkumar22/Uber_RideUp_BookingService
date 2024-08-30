package com.example.uberbokingservice.Repository;

import com.example.uber_rideupnewentityservice.Models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

}
