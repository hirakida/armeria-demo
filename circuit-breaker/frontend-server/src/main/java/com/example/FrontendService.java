package com.example;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.server.annotation.Get;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FrontendService {
    private final RestClient restClient;

    @Get("/")
    public CompletableFuture<JsonNode> hello() {
        return restClient.get("/")
                         .execute(JsonNode.class)
                         .handle((response, e) -> {
                             if (e != null) {
                                 return null;
                             }
                             return response.content();
                         });
    }
}
