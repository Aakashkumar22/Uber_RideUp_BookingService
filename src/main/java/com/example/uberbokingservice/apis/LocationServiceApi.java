package com.example.uberbokingservice.apis;

import com.example.uberbokingservice.dtos.DriverLocationdto;
import com.example.uberbokingservice.dtos.NearbyDriversRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceApi {

    @POST("/locations/nearby/drivers")
    Call<DriverLocationdto[]>getNearbyDrivers(@Body NearbyDriversRequestDto nearbyDriversRequestDto);
}
