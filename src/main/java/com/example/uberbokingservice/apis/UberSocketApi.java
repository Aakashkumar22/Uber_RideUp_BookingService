package com.example.uberbokingservice.apis;

import com.example.uberbokingservice.dtos.DriverLocationdto;
import com.example.uberbokingservice.dtos.NearbyDriversRequestDto;
import com.example.uberbokingservice.dtos.RideRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UberSocketApi {


        @POST("/Socket/newride")
        Call<Boolean> raiseRideRequest(@Body RideRequestDto rideRequestDto);

}
