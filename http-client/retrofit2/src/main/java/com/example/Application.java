package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {
    private final GitHubService gitHubService;

    @Override
    public void run(String... args) throws Exception {
        gitHubService.getUser("hirakida")
                     .handle((user, e) -> {
                         if (e != null) {
                             log.error("{}", e.getMessage(), e);
                         }
                         log.info("{}", user);
                         return user;
                     })
                     .join();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
