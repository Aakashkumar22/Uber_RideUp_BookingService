package com.example.uberbokingservice.controllers;


import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uberbokingservice.dtos.CreateBooking;
import com.example.uberbokingservice.dtos.CreateBookingResponse;
import com.example.uberbokingservice.dtos.UpdateBookingRequestdto;
import com.example.uberbokingservice.dtos.UpdateBookingdto;
import com.example.uberbokingservice.serives.BookingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Booking")
public class BookingController {

    private BookingServiceImpl bookingService;
    BookingController(BookingServiceImpl bookingService){
        this.bookingService=bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponse>creatBooking(@RequestBody CreateBooking createBookingdto){
        System.out.println(" inside controller");
        return new ResponseEntity<>(bookingService.creatBooking(createBookingdto), HttpStatus.CREATED);

    }
    @PatchMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingdto>updateBooking(@RequestBody UpdateBookingRequestdto updateBookingRequestdto,@PathVariable Long bookingId){
        System.out.println(" inside  update Booking controller");
        return new ResponseEntity<>(bookingService.updateBooking(updateBookingRequestdto,bookingId), HttpStatus.OK);

    }

}
