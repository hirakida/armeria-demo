package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerHttpClientBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerStrategy;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(ApiService apiService) {
        return builder -> builder.annotatedService(apiService);
    }

    @Bean
    public HttpClient httpClient() {
        final CircuitBreakerStrategy strategy = CircuitBreakerStrategy.onServerErrorStatus();
        return new HttpClientBuilder("http://localhost:8080")
                .decorator(LoggingClient.newDecorator())
                .decorator(new CircuitBreakerHttpClientBuilder(strategy).newDecorator())
                .build();
    }
}
