package com.example;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.Backoff;
import com.linecorp.armeria.client.retry.RetryConfig;
import com.linecorp.armeria.client.retry.RetryRule;
import com.linecorp.armeria.client.retry.RetryingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        RetryRule rule = RetryRule.builder()
                                  .onStatus(HttpStatus.UNAUTHORIZED)
                                  .thenBackoff(Backoff.ofDefault());
        RetryConfig<HttpResponse> config = RetryConfig.builder(rule)
                                                      .maxTotalAttempts(4)
                                                      .responseTimeoutForEachAttempt(Duration.ofMillis(3000))
                                                      .build();
        WebClient webClient = WebClient.builder("https://api.github.com")
                                       .decorator(LoggingClient.newDecorator())
                                       .decorator(RetryingClient.newDecorator(config))
                                       .build();

        AggregatedHttpResponse response = webClient.get("/user").aggregate().join();
        LOGGER.info("status={} content={}", response.status(), response.content().toStringUtf8());
    }
}
