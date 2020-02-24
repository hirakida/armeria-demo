package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    private static final String BASE_URL = "https://api.github.com/";

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(BASE_URL).build();
    }
}
