package com.example.uberbokingservice.configration;

import com.example.uberbokingservice.apis.LocationServiceApi;
import com.example.uberbokingservice.apis.UberSocketApi;
import com.netflix.discovery.EurekaClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {
    @Autowired
   private EurekaClient eurekaClient;

    private String getServiceUrl(String serviceName) {
        return eurekaClient.getNextServerFromEureka(serviceName,false).getHomePageUrl();

    }

    @Bean
    public LocationServiceApi locationServiceApi(){
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("UBER_RIDEUP-LOCATIONSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(LocationServiceApi.class);
    }
    @Bean
    public UberSocketApi uberSocketApi(){
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("UBER_RIDEUP-SOCKETSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(UberSocketApi.class);
    }

}
