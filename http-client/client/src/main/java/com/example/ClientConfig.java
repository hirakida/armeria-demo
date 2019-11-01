package com.example;

import java.time.Duration;

import javax.annotation.Nullable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ClientFactoryBuilder;
import com.linecorp.armeria.client.ClientOptions;
import com.linecorp.armeria.client.ClientOptionsBuilder;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.Backoff;
import com.linecorp.armeria.client.retry.RetryStrategy;
import com.linecorp.armeria.client.retry.RetryingHttpClient;
import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.HttpStatusClass;

@Configuration
public class ClientConfig {
    private static final String API_URL = "https://api.github.com";

    @Bean
    public HttpClient httpClient() {
        ClientFactory factory = new ClientFactoryBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .idleTimeout(Duration.ofSeconds(10))
                .build();
        ClientOptions options = new ClientOptionsBuilder()
                .decorator(LoggingClient.newDecorator())
                .responseTimeout(Duration.ofSeconds(10))
                .writeTimeout(Duration.ofSeconds(10))
                .setHttpHeader(HttpHeaderNames.AUTHORIZATION, "TOKEN")
                .build();
        return HttpClient.of(factory, API_URL, options);
    }

    @Bean
    public HttpClient retryHttpClient() {
        int maxTotalAttempts = 3;
        RetryStrategy retryStrategy = RetryStrategy.onStatus(ClientConfig::backoffFunction);
        return new HttpClientBuilder(API_URL)
                .decorator(LoggingClient.newDecorator())
                .decorator(RetryingHttpClient.newDecorator(retryStrategy, maxTotalAttempts))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Nullable
    private static Backoff backoffFunction(HttpStatus status, Throwable t) {
        if (t != null || (status != null && status.codeClass() != HttpStatusClass.SUCCESS)) {
            return Backoff.ofDefault();
        }
        return null;
    }
}
