package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.Backoff;
import com.linecorp.armeria.client.retry.RetryRule;
import com.linecorp.armeria.client.retry.RetryingClient;
import com.linecorp.armeria.common.HttpStatus;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient retryWebClient() {
        int maxTotalAttempts = 4;
        RetryRule rule = RetryRule.builder()
                                  .onStatus(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR)
                                  .thenBackoff(Backoff.ofDefault());

        return WebClient.builder("http://localhost:8080/")
                        .decorator(LoggingClient.newDecorator())
                        .decorator(RetryingClient.newDecorator(rule, maxTotalAttempts))
                        .build();
    }
}
