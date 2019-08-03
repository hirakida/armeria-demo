package com.example;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/forecast/webservice/json/v1")
    CompletableFuture<Map<String, Object>> getWeather(@Query("city") String city);
}
