package com.example;

import static com.example.BackendService.HEALTH_CHECK_PATH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpData;

@EnableScheduling
@SpringBootApplication
public class Application {
    private final WebClient webClient = WebClient.of("http://localhost:8081");

    @Scheduled(fixedRate = 10000)
    public void run() {
        webClient.put(HEALTH_CHECK_PATH, HttpData.EMPTY_DATA).aggregate().join();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
