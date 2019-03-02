package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.client.WeatherService;

import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofitBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class ClientConfig {

    @Bean
    public WeatherService weatherService() {
        Retrofit retrofit = new ArmeriaRetrofitBuilder()
                .baseUrl("http://weather.livedoor.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(WeatherService.class);
    }
}
