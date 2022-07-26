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
import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final RetryRule rule = RetryRule.builder()
                                        .onStatus(HttpStatus.UNAUTHORIZED)
                                        .thenBackoff(Backoff.ofDefault());
        final RetryConfig<HttpResponse> retryConfig =
                RetryConfig.builder(rule)
                           .maxTotalAttempts(4)
                           .responseTimeoutMillisForEachAttempt(3000)
                           .build();
        final WebClient webClient = WebClient.builder("https://api.github.com")
                                             .decorator(LoggingClient.newDecorator())
                                             .decorator(RetryingClient.newDecorator(retryConfig))
                                             .build();

        final AggregatedHttpResponse response = webClient.get("/user").aggregate().join();
        try (HttpData httpData = response.content()) {
            logger.info("status={} content={}", response.status(), httpData.toStringUtf8());
        }
    }
}
