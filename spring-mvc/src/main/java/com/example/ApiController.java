package com.example;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    private static final String DEFAULT_CITY = "400010";
    private final WeatherService weatherService;

    public ApiController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public Map<String, Object> getWeather(@RequestParam(defaultValue = DEFAULT_CITY) String city) {
        return weatherService.getWeather(city).join();
    }
}
