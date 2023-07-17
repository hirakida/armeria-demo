package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.Backoff;
import com.linecorp.armeria.client.retry.RetryRule;
import com.linecorp.armeria.client.retry.RetryingClient;
import com.linecorp.armeria.common.HttpStatus;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final RetryRule rule = RetryRule.builder()
                                        .onStatus(HttpStatus.UNAUTHORIZED)
                                        .thenBackoff(Backoff.ofDefault());
        final RestClient restClient =
                RestClient.builder("https://api.github.com")
                          .decorator(LoggingClient.newDecorator())
                          .decorator(RetryingClient.builder(rule)
                                                   .maxTotalAttempts(4)
                                                   .responseTimeoutMillisForEachAttempt(3000)
                                                   .newDecorator())
                          .build();

        final JsonNode response = restClient.get("/")
                                            .execute(JsonNode.class)
                                            .join()
                                            .content();
        logger.info("content={}", response);
    }
}
