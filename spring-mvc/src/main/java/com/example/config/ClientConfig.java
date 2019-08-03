package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.WeatherService;

import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofitBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class ClientConfig {
    private static final String API_URL = "http://weather.livedoor.com";

    @Bean
    public WeatherService weatherService() {
        final Retrofit retrofit = new ArmeriaRetrofitBuilder()
                .baseUrl(API_URL)
                .withClientOptions((string, builder) -> builder.decorator(LoggingClient.newDecorator()))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(WeatherService.class);
    }
}
