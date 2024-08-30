package com.example.uberbokingservice.serives;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uber_rideupnewentityservice.Models.BookingStatus;
import com.example.uber_rideupnewentityservice.Models.Driver;
import com.example.uber_rideupnewentityservice.Models.Passenger;
import com.example.uberbokingservice.Repository.BookingRepository;
import com.example.uberbokingservice.Repository.DriverRepository;
import com.example.uberbokingservice.Repository.PassengerRepository;
import com.example.uberbokingservice.apis.LocationServiceApi;
import com.example.uberbokingservice.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    //private static final String LOCATION_SERVICE="Http://localhost:2024";
    private final LocationServiceApi locationServiceApi;
    private final DriverRepository driverRepository;

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository,LocationServiceApi locationServiceApi,
                              DriverRepository driverRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi=locationServiceApi;
        this.driverRepository = driverRepository;
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
        processNearbyDriversAsync(nearbydrivers);

         /*ResponseEntity<DriverLocationdto[]> result=restTemplate.postForEntity(LOCATION_SERVICE+"/locations/nearby/drivers",nearbydrivers, DriverLocationdto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
           List<DriverLocationdto> driverLocations = Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
           });
        }*/




         return CreateBookingResponse.builder().
                 bookingID(newbooking.getId())
                 .BookingStatus(newbooking.getBookingStatus().toString())
                 // .driver(Optional.of(newbooking.getDriver()))
                 .build();


    }



    private void processNearbyDriversAsync(NearbyDriversRequestDto nearbyDriversRequestDto){
        Call<DriverLocationdto[]>call= locationServiceApi.getNearbyDrivers(nearbyDriversRequestDto);
        call.enqueue(new Callback<DriverLocationdto[]>() {
            @Override
            public void onResponse(Call<DriverLocationdto[]> call, Response<DriverLocationdto[]> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<DriverLocationdto> driverLocations = Arrays.asList(response.body());
                    driverLocations.forEach(driverLocationDto -> {
                        System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
                    });
            }
                else{
                    System.out.println("Request Failed"+response.message());
                }
                }

            @Override
            public void onFailure(Call<DriverLocationdto[]> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    @Override
    public UpdateBookingdto updateBooking(UpdateBookingRequestdto updateBookingDetails,Long bookingId) {

            Optional<Driver>driver=driverRepository.findById(updateBookingDetails.getDriverId().get());
            bookingRepository.updateBookingStatusAndDriverById(bookingId,updateBookingDetails.getBookingStatus(),driver.get());
            Optional<Booking>booking=bookingRepository.findById(bookingId);
            return UpdateBookingdto.builder()
                    .bookingId(bookingId)
                    .bookingStatus(booking.get().getBookingStatus())
                    .driver(Optional.ofNullable(booking.get().getDriver()))
                    .build();


    }
}
