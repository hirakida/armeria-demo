package com.example;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ClientFactoryBuilder;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofitBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class ClientConfig {
    private static final String API_URL = "http://weather.livedoor.com";

    @Bean
    public HttpClient httpClient() {
        final ClientFactory factory = new ClientFactoryBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .idleTimeout(Duration.ofSeconds(10))
                .build();
        return new HttpClientBuilder(API_URL)
                .decorator(LoggingClient.newDecorator())
                .responseTimeout(Duration.ofSeconds(10))
                .writeTimeout(Duration.ofSeconds(10))
                .factory(factory)
                .build();
    }

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
