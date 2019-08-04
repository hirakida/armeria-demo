package com.example;

import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;

@EnableScheduling
@Component
public class ScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final HttpClient httpClient;

    public ScheduledTask(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Scheduled(fixedRate = 1000)
    public void run() {
        try {
            final AggregatedHttpResponse response = httpClient.get("/").aggregate().join();
            if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR) {
                logger.warn("{}", response.status());
            }
        } catch (CompletionException e) {
            logger.error(e.getMessage());
        }
    }
}
