package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerHttpClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerStrategy;
import com.linecorp.armeria.client.circuitbreaker.MetricCollectingCircuitBreakerListener;
import com.linecorp.armeria.client.logging.LoggingClient;

import io.micrometer.core.instrument.Metrics;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient webClient() {
        CircuitBreaker circuitBreaker =
                CircuitBreaker.builder()
                              .listener(new MetricCollectingCircuitBreakerListener(Metrics.globalRegistry))
                              .build();
        CircuitBreakerStrategy strategy = CircuitBreakerStrategy.onServerErrorStatus();
        return WebClient.builder("http://localhost:8080")
                        .decorator(LoggingClient.newDecorator())
                        .decorator(CircuitBreakerHttpClient.newDecorator(circuitBreaker, strategy))
                        .build();
    }
}
