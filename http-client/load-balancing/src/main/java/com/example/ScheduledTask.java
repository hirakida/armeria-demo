package com.example;

import static com.example.backend.BackendService.HEALTH_CHECK_PATH;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpData;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final WebClient webClient = WebClient.of("http://localhost:8081");

    @Scheduled(fixedRate = 10000)
    public void run() {
        webClient.put(HEALTH_CHECK_PATH, HttpData.empty()).aggregate().join();
    }
}
