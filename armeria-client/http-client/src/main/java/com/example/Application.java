package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Override
    public void run(String... args) throws Exception {
        AggregatedHttpResponse response = httpClient.get("/users/hirakida")
                                                    .aggregate()
                                                    .join();
        User user = objectMapper.readValue(response.content().toReaderUtf8(), User.class);
        log.info("{}", user);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
