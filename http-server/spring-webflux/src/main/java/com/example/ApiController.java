package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.WeatherApiClient;
import com.example.client.model.Weather;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final WeatherApiClient weatherApiClient;

    @GetMapping("/weather")
    public Mono<Weather> getWeather(@RequestParam(defaultValue = "400010") String city) {
        return weatherApiClient.getWeather(city);
    }
}
