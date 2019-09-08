package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private final HttpClient httpClient;

    public Application(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void run(String... args) throws Exception {
        final AggregatedHttpResponse response = httpClient.get("/").aggregate().join();
        if (response.status() != HttpStatus.OK) {
            log.error("{}", response.status());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
