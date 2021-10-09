package com.example;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class GitHubApiClient {
    private static final String BASE_URL = "https://api.github.com/";
    private final WebClient webClient;

    public GitHubApiClient(WebClient.Builder builder) {
        webClient = builder.baseUrl(BASE_URL).build();
    }

    public Mono<User> getUser(String username) {
        return webClient.get()
                        .uri("/users/{username}", username)
                        .retrieve()
                        .bodyToMono(User.class);
    }
}
