package com.example;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerListener;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerRule;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.LoggingService;

import io.micrometer.core.instrument.Metrics;

public final class Main {
    public static void main(String[] args) {
        final Server server =
                Server.builder()
                      .http(8080)
                      .serviceUnder("/docs", DocService.builder().build())
                      .decorator(LoggingService.newDecorator())
                      .annotatedService(new BackendService())
                      .annotatedService(new CircuitBreakerService(createRestClient()))
                      .build();
        server.start().join();
    }

    private static RestClient createRestClient() {
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
