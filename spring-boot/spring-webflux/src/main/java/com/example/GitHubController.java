package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GitHubController {
    private final GitHubApiClient gitHubApiClient;

    @GetMapping("/users/{username}")
    public Mono<User> getUser(@PathVariable String username) {
        return gitHubApiClient.getUser(username);
    }
}
