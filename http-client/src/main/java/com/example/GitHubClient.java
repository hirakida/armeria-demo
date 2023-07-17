package com.example;

import static com.example.LoggingDecorator.USERNAME_ATTR;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.model.Repo;
import com.example.model.User;
import com.fasterxml.jackson.core.type.TypeReference;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.common.ResponseEntity;

public class GitHubClient {
    private final TypeReference<List<Repo>> typeReference = new TypeReference<>() {};
    private final RestClient restClient;

    public GitHubClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CompletableFuture<ResponseEntity<User>> getUser(String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(User.class);
    }

    public CompletableFuture<ResponseEntity<User>> getUserWithAttr(String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .attr(USERNAME_ATTR, username)
                         .execute(User.class);
    }

    public CompletableFuture<ResponseEntity<List<Repo>>> getRepos(String username) {
        return restClient.get("/users/{username}/repos")
                         .pathParam("username", username)
                         .execute(typeReference);
    }
}
