package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application {
    private final WebClient webClient;

    @Scheduled(fixedRate = 20000)
    public void run() {
        final AggregatedHttpResponse response = webClient.get("/").aggregate().join();
        log.info("{}", response);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
