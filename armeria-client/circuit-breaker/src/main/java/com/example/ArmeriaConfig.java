package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerHttpClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerStrategy;
import com.linecorp.armeria.client.circuitbreaker.MetricCollectingCircuitBreakerListener;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.micrometer.core.instrument.Metrics;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(ApiService apiService) {
        return builder -> builder.annotatedService(apiService);
    }

    @Bean
    public HttpClient httpClient() {
        final CircuitBreaker circuitBreaker = new CircuitBreakerBuilder()
                .listener(new MetricCollectingCircuitBreakerListener(Metrics.globalRegistry))
                .build();
        final CircuitBreakerStrategy strategy = CircuitBreakerStrategy.onServerErrorStatus();
        return new HttpClientBuilder("http://localhost:8080")
                .decorator(LoggingClient.newDecorator())
                .decorator(CircuitBreakerHttpClient.newDecorator(circuitBreaker, strategy))
                .build();
    }
}
