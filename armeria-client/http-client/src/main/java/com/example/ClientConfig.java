package com.example;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ClientFactoryBuilder;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.HttpHeaderNames;

@Configuration
public class ClientConfig {
    private static final String API_URL = "https://api.github.com";

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
                .setHttpHeader(HttpHeaderNames.AUTHORIZATION, "TOKEN")
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
