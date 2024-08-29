package com.example.uberbokingservice.serives;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uber_rideupnewentityservice.Models.BookingStatus;
import com.example.uber_rideupnewentityservice.Models.Passenger;
import com.example.uberbokingservice.Repository.BookingRepository;
import com.example.uberbokingservice.Repository.PassengerRepository;
import com.example.uberbokingservice.dtos.CreateBooking;
import com.example.uberbokingservice.dtos.CreateBookingResponse;
import com.example.uberbokingservice.dtos.DriverLocationdto;
import com.example.uberbokingservice.dtos.NearbyDriversRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private static final String LOCATION_SERVICE="Http://localhost:2024";

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
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

        NearbyDriversRequestDto nearbydrivers= NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getEndLocation().getLongitude())
                .build();

         ResponseEntity<DriverLocationdto[]> result=restTemplate.postForEntity(LOCATION_SERVICE+"/locations/nearby/drivers",nearbydrivers, DriverLocationdto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
           List<DriverLocationdto> driverLocations = Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
           });
        }




         return CreateBookingResponse.builder().
                 bookingID(newbooking.getId())
                 .BookingStatus(newbooking.getBookingStatus().toString())
                 // .driver(Optional.of(newbooking.getDriver()))
                 .build();


    }
}
