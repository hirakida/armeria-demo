package com.example.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.client.model.Weather;

import reactor.core.publisher.Mono;

@Component
public class WeatherApiClient {
    private final WebClient webClient;

    public WeatherApiClient(WebClient.Builder builder) {
        webClient = builder.baseUrl("http://weather.livedoor.com/").build();
    }

    public Mono<Weather> getWeather(String city) {
        return webClient.get()
                        .uri(builder -> builder.path("/forecast/webservice/json/v1")
                                               .queryParam("city", city)
                                               .build())
                        .retrieve()
                        .bodyToMono(Weather.class);
    }
}
