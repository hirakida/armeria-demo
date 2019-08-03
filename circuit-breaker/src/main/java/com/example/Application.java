package com.example;

import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;

@EnableScheduling
@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final HttpClient httpClient;

    public Application(HttpClient httpClient) {
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
