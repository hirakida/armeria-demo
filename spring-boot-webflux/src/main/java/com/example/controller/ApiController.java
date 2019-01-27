package com.example.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.Weather;
import com.example.client.WeatherApiClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private static final String DEFAULT_CODE = "400010";
    private final WeatherApiClient weatherApiClient;

    @GetMapping("/weather")
    public Mono<Weather> getWeather(@RequestParam Optional<String> code) {
        return weatherApiClient.getWeather(code.orElse(DEFAULT_CODE));
    }
}
