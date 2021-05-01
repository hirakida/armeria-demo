package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retry.Backoff;
import com.linecorp.armeria.client.retry.RetryConfig;
import com.linecorp.armeria.client.retry.RetryRule;
import com.linecorp.armeria.client.retry.RetryingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String BASE_URL = "https://api.github.com";

    @Override
    public void run(String... args) throws Exception {
        WebClient webClient = createWebClient();
        AggregatedHttpResponse response = webClient.get("/user").aggregate().join();
        log.info("status={} content={}", response.status(), response.content().toStringUtf8());
    }

    private static WebClient createWebClient() {
        RetryRule rule = RetryRule.builder()
                                  .onStatus(HttpStatus.UNAUTHORIZED)
                                  .thenBackoff(Backoff.ofDefault());
        RetryConfig<HttpResponse> config = RetryConfig.builder(rule)
                                                      .maxTotalAttempts(4)
                                                      .build();
        return WebClient.builder(BASE_URL)
                        .decorator(LoggingClient.newDecorator())
                        .decorator(RetryingClient.newDecorator(config))
                        .build();
    }
}
