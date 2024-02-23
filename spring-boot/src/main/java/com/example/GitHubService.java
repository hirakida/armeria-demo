package com.example;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

@Component
public class GitHubService {
    private final RestClient restClient;

    public GitHubService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Get("/users/{username}")
    public JsonNode getUser(@Param String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(JsonNode.class)
                         .join()
                         .content();
    }
}
