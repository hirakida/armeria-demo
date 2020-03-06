package com.example;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    private final WebClient webClient;

    @Scheduled(fixedRate = 20000)
    public void run() {
        final AggregatedHttpResponse response = webClient.get("/hello").aggregate().join();
        log.info("status={} content={}", response.status(), response.content().toStringUtf8());
    }
}
