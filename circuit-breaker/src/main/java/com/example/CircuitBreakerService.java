package com.example;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.server.annotation.Get;

@Component
public class CircuitBreakerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestClient restClient;

    public CircuitBreakerService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Get("/")
    public CompletableFuture<JsonNode> get() {
        return restClient.get("/backend")
                         .execute(JsonNode.class)
                         .handle((response, e) -> {
                             if (e != null) {
                                 return objectMapper.valueToTree(Map.of("message", e.getMessage()));
                             }
                             return response.content();
                         });
    }
}
