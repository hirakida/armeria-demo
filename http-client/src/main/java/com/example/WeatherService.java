package com.example;

import java.util.concurrent.CompletableFuture;

import com.example.model.Weather;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/forecast/webservice/json/v1")
    CompletableFuture<Weather> getWeather(@Query("city") String city);
}
