package com.example;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.server.annotation.Get;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CircuitBreakerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestClient restClient;

    @Get("/")
    public CompletableFuture<JsonNode> get() {
        return restClient.get("/backend")
                         .execute(JsonNode.class)
                         .handle((response, e) -> {
                             if (e != null) {
                                 log.warn("{}", e.getMessage());
                                 return objectMapper.valueToTree(Map.of("message", e.getMessage()));
                             }
                             return response.content();
                         });
    }
}
