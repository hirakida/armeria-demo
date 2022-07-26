package com.example;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.common.HttpEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GitHubClient {
    private final RestClient restClient;

    public CompletableFuture<User> getUser(String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(User.class)
                         .thenApply(HttpEntity::content);
    }
}
