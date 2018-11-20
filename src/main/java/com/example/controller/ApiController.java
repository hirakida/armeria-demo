package com.example.controller;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.Weather;
import com.example.client.WeatherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private static final String DEFAULT_CODE = "400010";
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam Optional<String> code)
            throws InterruptedException, ExecutionException {
        return weatherService.getWeather(code.orElse(DEFAULT_CODE)).get();
    }
}
