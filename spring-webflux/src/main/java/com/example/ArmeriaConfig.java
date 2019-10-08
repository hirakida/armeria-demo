package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ClientFactoryBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerHttpClient;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerStrategy;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.web.reactive.ArmeriaClientConfigurator;

import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        return builder -> {
            builder.serviceUnder("/docs", new DocService());
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.combined(), false);
        };
    }

    @Bean
    public ClientFactory clientFactory() {
        return new ClientFactoryBuilder()
                .sslContextCustomizer(builder -> builder.trustManager(InsecureTrustManagerFactory.INSTANCE))
                .build();
    }

    @Bean
    public ArmeriaClientConfigurator armeriaClientConfigurator(ClientFactory clientFactory) {
        return builder -> {
            final CircuitBreakerStrategy strategy = CircuitBreakerStrategy.onServerErrorStatus();
            builder.decorator(CircuitBreakerHttpClient.builder(strategy).newDecorator());
            builder.factory(clientFactory);
        };
    }
}
