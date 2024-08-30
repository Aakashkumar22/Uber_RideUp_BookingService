package com.example.uberbokingservice.Repository;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uber_rideupnewentityservice.Models.BookingStatus;
import com.example.uber_rideupnewentityservice.Models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking ,Long> {

    @Modifying// using it to tell that onlu update otherwise bydefault it will consider it select
    @Transactional //for any delete and update  query its a proper transaction so use it
    @Query("UPDATE Booking b SET b.bookingStatus = :status , b.driver = :driver  WHERE b.id = :id ")
    void updateBookingStatusAndDriverById(@Param("id") Long id, @Param("status") BookingStatus  status, @Param("driver") Driver driver);

}

