package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerListener;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerRule;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.micrometer.core.instrument.Metrics;

@Configuration
public class ArmeriaConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(FrontendService frontendService) {
        return builder -> builder.annotatedService(frontendService);
    }

    @Bean
    public WebClient webClient() {
        CircuitBreakerListener listener = CircuitBreakerListener.metricCollecting(Metrics.globalRegistry);
        CircuitBreaker circuitBreaker = CircuitBreaker.builder()
                                                      .listener(listener)
                                                      .build();
        CircuitBreakerRule rule = CircuitBreakerRule.builder()
                                                    .onServerErrorStatus()
                                                    .onException()
                                                    .thenFailure();

        return WebClient.builder("http://localhost:8081")
                        .decorator(LoggingClient.newDecorator())
                        .decorator(CircuitBreakerClient.newDecorator(circuitBreaker, rule))
                        .build();
    }
}
