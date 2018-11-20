package com.example.client;

import java.util.concurrent.CompletableFuture;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/forecast/webservice/json/v1")
    CompletableFuture<Weather> getWeather(@Query("city") String code);
}
