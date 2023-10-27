package com.example;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.ProducesJson;

public class GitHubService {
    private final RestClient restClient;

    public GitHubService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Get("/github/{username}")
    @ProducesJson
    public JsonNode getGitHubUser(@Param("username") String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(JsonNode.class)
                         .join()
                         .content();
    }
}
