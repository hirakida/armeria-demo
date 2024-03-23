package com.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.server.annotation.Get;

public class BackendService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final int port;

    public BackendService(int port) {
        this.port = port;
    }

    @Get("/")
    public JsonNode get() {
        return objectMapper.createObjectNode()
                           .put("message", "localhost:" + port);
    }
}
