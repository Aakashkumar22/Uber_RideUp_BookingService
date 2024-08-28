package com.example.uberbokingservice.serives;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uber_rideupnewentityservice.Models.BookingStatus;
import com.example.uber_rideupnewentityservice.Models.Passenger;
import com.example.uberbokingservice.Repository.BookingRepository;
import com.example.uberbokingservice.Repository.PassengerRepository;
import com.example.uberbokingservice.dtos.CreateBooking;
import com.example.uberbokingservice.dtos.CreateBookingResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public CreateBookingResponse creatBooking(CreateBooking bookingDetails) {
        Optional<Passenger>passenger=passengerRepository.findById(bookingDetails.getPassengerID());
        Booking booking= Booking.builder()
                .bookingStatus(BookingStatus.AVAILABLE)
                .startLoaction(bookingDetails.getStartLocation())
                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
         Booking newbooking =bookingRepository.save(booking);
         return CreateBookingResponse.builder().
                 bookingID(newbooking.getId())
                 .BookingStatus(newbooking.getBookingStatus().toString())
                 .driver(Optional.of(newbooking.getDriver()))
                 .build();


    }
}
