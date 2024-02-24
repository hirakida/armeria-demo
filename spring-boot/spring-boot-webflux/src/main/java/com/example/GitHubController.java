package com.example;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.common.HttpEntity;

@RestController
public class GitHubController {
    private final RestClient restClient;

    public GitHubController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/users/{username}")
    public CompletableFuture<JsonNode> getUser(@PathVariable String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(JsonNode.class)
                         .thenApply(HttpEntity::content);
    }
}
