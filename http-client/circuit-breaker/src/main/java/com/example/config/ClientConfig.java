package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerRule;
import com.linecorp.armeria.client.circuitbreaker.MetricCollectingCircuitBreakerListener;
import com.linecorp.armeria.client.logging.LoggingClient;

import io.micrometer.core.instrument.Metrics;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient webClient() {
        MetricCollectingCircuitBreakerListener listener =
                new MetricCollectingCircuitBreakerListener(Metrics.globalRegistry);
        CircuitBreaker circuitBreaker = CircuitBreaker.builder()
                                                      .listener(listener)
                                                      .build();
        CircuitBreakerRule rule = CircuitBreakerRule.builder()
                                                    .onServerErrorStatus()
                                                    .onException()
                                                    .thenFailure();

        return WebClient.builder("http://localhost:8080")
                        .decorator(LoggingClient.newDecorator())
                        .decorator(CircuitBreakerClient.newDecorator(circuitBreaker, rule))
                        .build();
    }
}
