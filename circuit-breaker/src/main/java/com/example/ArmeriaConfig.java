package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerListener;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerRule;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.micrometer.core.instrument.Metrics;

@Configuration
public class ArmeriaConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(CircuitBreakerService circuitBreakerService,
                                                               BackendService backendService) {
        return builder -> builder.annotatedService(circuitBreakerService)
                                 .annotatedService(backendService);
    }

    @Bean
    public RestClient restClient() {
        final CircuitBreakerListener listener = CircuitBreakerListener.metricCollecting(Metrics.globalRegistry);
        final CircuitBreaker circuitBreaker = CircuitBreaker.builder()
                                                            .listener(listener)
                                                            .build();
        final CircuitBreakerRule rule = CircuitBreakerRule.builder()
                                                          .onServerErrorStatus()
                                                          .onException()
                                                          .thenFailure();

        return RestClient.builder("http://localhost:8080/")
                         .decorator(LoggingClient.builder()
                                                 .requestLogLevel(LogLevel.INFO)
                                                 .successfulResponseLogLevel(LogLevel.INFO)
                                                 .failureResponseLogLevel(LogLevel.WARN)
                                                 .newDecorator())
                         .decorator(CircuitBreakerClient.newDecorator(circuitBreaker, rule))
                         .build();
    }
}
