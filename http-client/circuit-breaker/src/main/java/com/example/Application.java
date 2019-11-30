package com.example;

import java.util.concurrent.CompletionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application {
    private final WebClient webClient;

    @Scheduled(fixedRate = 1000)
    public void run() {
        try {
            final AggregatedHttpResponse response = webClient.get("/").aggregate().join();
            if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR) {
                log.error("{}", response.status());
            }
        } catch (CompletionException e) {
            log.warn(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
