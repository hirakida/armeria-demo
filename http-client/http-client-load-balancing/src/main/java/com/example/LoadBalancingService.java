package com.example;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.common.HttpEntity;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;

public class LoadBalancingService {
    private final RestClient restClient;

    public LoadBalancingService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Get("/")
    @ProducesJson
    public CompletableFuture<JsonNode> get() {
        return restClient.get("/backend")
                         .execute(JsonNode.class)
                         .thenApply(HttpEntity::content);
    }
}
