package com.example;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GitHubController {
    private final GitHubClient client;

    @GetMapping("/users/{username}")
    public CompletableFuture<User> getUser(@PathVariable String username) {
        return client.getUser(username);
    }
}
