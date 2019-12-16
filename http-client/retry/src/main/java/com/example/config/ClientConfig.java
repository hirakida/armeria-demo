package com.example.config;

import javax.annotation.Nullable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.Backoff;
import com.linecorp.armeria.client.retry.RetryStrategy;
import com.linecorp.armeria.client.retry.RetryingHttpClient;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.HttpStatusClass;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient retryWebClient() {
        int maxTotalAttempts = 4;
        RetryStrategy retryStrategy = RetryStrategy.onStatus(ClientConfig::backoffFunction);
        return WebClient.builder("http://localhost:8080/")
                        .decorator(LoggingClient.newDecorator())
                        .decorator(RetryingHttpClient.newDecorator(retryStrategy, maxTotalAttempts))
                        .build();
    }

    @Nullable
    private static Backoff backoffFunction(HttpStatus status, Throwable t) {
        if (t != null || (status != null && status.codeClass() != HttpStatusClass.SUCCESS)) {
            return Backoff.ofDefault();
        }
        return null;
    }
}
