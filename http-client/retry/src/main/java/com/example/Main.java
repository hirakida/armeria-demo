package com.example;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.Backoff;
import com.linecorp.armeria.client.retry.RetryRule;
import com.linecorp.armeria.client.retry.RetryingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        WebClient webClient = createWebClient();
        AggregatedHttpResponse response = webClient.get("/user").aggregate().join();
        log.info("status={} content={}", response.status(), response.content().toStringUtf8());
    }

    private static WebClient createWebClient() {
        int maxTotalAttempts = 4;
        RetryRule rule = RetryRule.builder()
                                  .onStatus(HttpStatus.UNAUTHORIZED)
                                  .thenBackoff(Backoff.ofDefault());

        return WebClient.builder("https://api.github.com/")
                        .decorator(LoggingClient.newDecorator())
                        .decorator(RetryingClient.newDecorator(rule, maxTotalAttempts))
                        .build();
    }
}