package com.example;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ClientOptions;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;

@Configuration
public class ClientConfig {
    private static final String BASE_URL = "https://api.github.com";

    @Bean
    public WebClient webClient() {
        ClientFactory factory = ClientFactory.builder()
                                             .connectTimeout(Duration.ofSeconds(10))
                                             .idleTimeout(Duration.ofSeconds(10))
                                             .build();
        ClientOptions options = ClientOptions.builder()
                                             .decorator(LoggingClient.newDecorator())
                                             .responseTimeout(Duration.ofSeconds(10))
                                             .writeTimeout(Duration.ofSeconds(10))
                                             .build();
        return WebClient.builder(BASE_URL)
                        .factory(factory)
                        .options(options)
                        .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
