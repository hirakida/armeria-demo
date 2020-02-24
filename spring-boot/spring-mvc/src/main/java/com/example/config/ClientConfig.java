package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.WebClient;

@Configuration
public class ClientConfig {
    private static final String BASE_URL = "https://api.github.com";

    @Bean
    public WebClient webClient() {
        return WebClient.of(BASE_URL);
    }
}
