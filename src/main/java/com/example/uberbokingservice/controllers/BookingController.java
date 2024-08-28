package com.example.uberbokingservice.controllers;


import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uberbokingservice.dtos.CreateBooking;
import com.example.uberbokingservice.dtos.CreateBookingResponse;
import com.example.uberbokingservice.serives.BookingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Booking")
public class BookingController {

    private BookingServiceImpl bookingService;
    BookingController(BookingServiceImpl bookingService){
        this.bookingService=bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponse>creatBooking(@RequestBody CreateBooking createBookingdto){
        return new ResponseEntity<>(bookingService.creatBooking(createBookingdto), HttpStatus.CREATED);

    }

}
