package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.RetryStrategy;
import com.linecorp.armeria.client.retry.RetryingHttpClient;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        return builder -> builder.annotatedService(new DemoService());
    }

    @Bean
    public HttpClient httpClient() {
        final int maxTotalAttempts = 4;
        final RetryStrategy strategy = RetryStrategy.onServerErrorStatus();
        return new HttpClientBuilder("http://localhost:8080")
                .decorator(LoggingClient.newDecorator())
                .decorator(RetryingHttpClient.newDecorator(strategy, maxTotalAttempts))
                .build();
    }
}
