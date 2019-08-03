package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.model.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {
    private static final String CITY_ID = "400010";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient;
    private final WeatherService weatherService;

    @Override
    public void run(String... args) throws Exception {
        // HttpClient
        AggregatedHttpResponse response = httpClient.get("/forecast/webservice/json/v1?city=" + CITY_ID)
                                                    .aggregate().join();
        Weather weather = objectMapper.readValue(response.content().toReaderUtf8(), Weather.class);
        log.info("{}", weather);

        // Retrofit
        weather = weatherService.getWeather(CITY_ID).get();
        log.info("{}", weather);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
