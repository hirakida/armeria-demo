package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerHttpClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerStrategy;
import com.linecorp.armeria.client.circuitbreaker.MetricCollectingCircuitBreakerListener;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.micrometer.core.instrument.Metrics;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        return builder -> builder.annotatedService(new DemoService());
    }

    @Bean
    public WebClient httpClient() {
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
