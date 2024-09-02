package com.example.uberbokingservice.serives;

import com.example.uber_rideupnewentityservice.Models.Booking;
import com.example.uber_rideupnewentityservice.Models.BookingStatus;
import com.example.uber_rideupnewentityservice.Models.Driver;
import com.example.uber_rideupnewentityservice.Models.Passenger;
import com.example.uberbokingservice.Repository.BookingRepository;
import com.example.uberbokingservice.Repository.DriverRepository;
import com.example.uberbokingservice.Repository.PassengerRepository;
import com.example.uberbokingservice.apis.LocationServiceApi;
import com.example.uberbokingservice.apis.UberSocketApi;
import com.example.uberbokingservice.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.awt.*;
import java.io.IOException;
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
    private final UberSocketApi uberSocketApi;

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository,LocationServiceApi locationServiceApi,
                              DriverRepository driverRepository,UberSocketApi uberSocketApi) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi=locationServiceApi;
        this.driverRepository = driverRepository;
        this.uberSocketApi=uberSocketApi;
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
        processNearbyDriversAsync(nearbydrivers, bookingDetails.getPassengerID(), newbooking.getId());

         /*ResponseEntity<DriverLocationdto[]> result=restTemplate.postForEntity(LOCATION_SERVICE+"/locations/nearby/drivers",nearbydrivers, DriverLocationdto[].class);


        }*/




         return CreateBookingResponse.builder().
                 bookingID(newbooking.getId())
                 .BookingStatus(newbooking.getBookingStatus().toString())
                 // .driver(Optional.of(newbooking.getDriver()))
                 .build();


    }



    private void processNearbyDriversAsync(NearbyDriversRequestDto nearbyDriversRequestDto,Long passengerID,Long bookingID){
        Call<DriverLocationdto[]>call= locationServiceApi.getNearbyDrivers(nearbyDriversRequestDto);
        call.enqueue(new Callback<DriverLocationdto[]>() {
            @Override
            public void onResponse(Call<DriverLocationdto[]> call, Response<DriverLocationdto[]> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<DriverLocationdto> driverLocations = Arrays.asList(response.body());
                    driverLocations.forEach(driverLocationDto -> {
                        System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
                    });
                    try{
                        raiseRideRequestAsync(RideRequestDto.builder().passengerID(passengerID).bookingID(bookingID).build());

                    }
                    catch (IOException e){
                        throw new RuntimeException(e);
                    }

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
                    //.driver(Optional.ofNullable(booking.get().getDriver()))
                    .build();


    }

    private void raiseRideRequestAsync(RideRequestDto rideRequestDto) throws  IOException{
        Call<Boolean>call= uberSocketApi.raiseRideRequest(rideRequestDto);
        System.out.println(call.request().url()+" "+call.request().body()+" "+call.request().headers());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.isSuccessful());
                System.out.println(response.body());
                if(response.isSuccessful() && response.body() != null) {
                    Boolean result=response.body();
                    System.out.println(" Driver response is "+result.toString());
                }
                else{
                    System.out.println("Request Failed"+response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
