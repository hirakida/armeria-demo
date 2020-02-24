package com.example;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GitHubApiClient {
    private final WebClient webClient;

    public Mono<User> getUser(String username) {
        return webClient.get()
                        .uri("/users/{username}", username)
                        .retrieve()
                        .bodyToMono(User.class);
    }
}
