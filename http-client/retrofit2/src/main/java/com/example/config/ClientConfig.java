package com.example.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.GitHubService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class ClientConfig {
    private static final String BASE_URL = "https://api.github.com";

    @Bean
    public GitHubService gitHubService(ObjectMapper objectMapper) {
        final Retrofit retrofit =
                ArmeriaRetrofit.builder(BASE_URL)
                               .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                               .decorator(LoggingClient.newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .build();
        return retrofit.create(GitHubService.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
